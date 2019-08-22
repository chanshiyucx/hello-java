package com.chanshiyu;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    private final String customRealmName = "customRealm";

    {
        super.setName(customRealmName);
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.从主体传过来的认证信息获取用户名
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        // 2.通过用户名从数据库中获取凭证
        SimpleAuthenticationInfo info = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql ="select id, username, password from users where username = ?";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            String password = null;
            for(boolean foundResult = false; resultSet.next(); foundResult = true) {
                if (foundResult) {
                    throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                }
                password = resultSet.getString("password");
            }
            if (password == null) {
                return null;
            }

            info = new SimpleAuthenticationInfo(username, password, customRealmName);
            info.setCredentialsSalt(ByteSource.Util.bytes("shiro"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(resultSet, preparedStatement, connection);
        }

        return info;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = null;
        Connection conn = null;
        try {
            // 2.通过用户名获取角色
            conn = JDBCUtil.getConnection();
            Set<String> roleNames = getRoleNamesForUser(conn, username);

            // 3. 通过角色获取权限
            Set permissions = getPermissions(conn, username, roleNames);

             info = new SimpleAuthorizationInfo(roleNames);
            info.setStringPermissions(permissions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(null, null, conn);
        }
        return info;
    }

    /**
     * 获取角色
     * @param conn
     * @param username
     * @return
     * @throws SQLException
     */
    private Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedHashSet roleNames = new LinkedHashSet();
        String sql ="select role_name from user_roles where username = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            while(rs.next()) {
                String roleName = rs.getString(1);
                if (roleName != null) {
                    roleNames.add(roleName);
                } else {
                    System.out.println("Null role name found while retrieving role names for user [" + username + "]");
                }
            }
        } finally {
            JDBCUtil.release(rs, ps, null);
        }

        return roleNames;
    }

    /**
     * 获取权限
     * @param conn
     * @param username
     * @param roleNames
     * @return
     * @throws SQLException
     */
    private Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        PreparedStatement ps = null;
        LinkedHashSet permissions = new LinkedHashSet();
        String sql ="select permission from roles_permissions where role_name = ?";

        try {
            ps = conn.prepareStatement(sql);
            Iterator roleIt = roleNames.iterator();

            while(roleIt.hasNext()) {
                String roleName = (String)roleIt.next();
                ps.setString(1, roleName);
                ResultSet rs = null;

                try {
                    rs = ps.executeQuery();
                    while(rs.next()) {
                        String permissionString = rs.getString(1);
                        permissions.add(permissionString);
                    }
                } finally {
                    JDBCUtil.release(rs, null, null);
                }
            }
        } finally {
            JDBCUtil.release(null, ps, null);
        }

        return permissions;
    }
}
