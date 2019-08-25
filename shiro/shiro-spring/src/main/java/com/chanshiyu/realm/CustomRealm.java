package com.chanshiyu.realm;

import com.chanshiyu.service.UserService;
import com.chanshiyu.vo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    private final String customRealmName = "customRealm";

    @Resource
    private UserService userService;

    {
        super.setName(customRealmName);
    }

    /**
     * 认证
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
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(), customRealmName);
        info.setCredentialsSalt(ByteSource.Util.bytes("shiro"));
        return info;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = null;
        try {
            // 2.通过用户名获取角色
            Set<String> roleNames = getRoleNamesForUser(username);

            // 3. 通过角色获取权限
            Set<String> permissions = getPermissions(roleNames);

            info = new SimpleAuthorizationInfo(roleNames);
            info.setStringPermissions(permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取角色
     */
    private Set<String> getRoleNamesForUser(String username) throws SQLException {
        System.out.println("从数据库中获取权限数据");
        List<String> roles = userService.getRoleByUsername(username);
        LinkedHashSet sets = new LinkedHashSet(roles);
        return sets;
    }

    /**
     * 获取权限
     */
    private Set<String> getPermissions(Set<String> roles) throws SQLException {
        List<String> permissions = userService.getPermissionByRole(roles);
        LinkedHashSet sets = new LinkedHashSet(permissions);
        return sets;
    }
}
