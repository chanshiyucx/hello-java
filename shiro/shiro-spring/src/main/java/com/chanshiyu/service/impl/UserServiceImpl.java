package com.chanshiyu.service.impl;

import com.chanshiyu.service.UserService;
import com.chanshiyu.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        String sql = "select id, username, password from users where username = ?";

        List<User> users = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                return user;
            }
        });

        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.get(0);
    }

    public List<String> getRoleByUsername(String username) {
        String sql ="select role_name from user_roles where username = ?";
        List<String> roles = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String role = resultSet.getString("role_name");
                return role;
            }
        });
        return roles;
    }

    public List<String> getPermissionByRole(Set<String> roles) {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("roles", roles);

        String sql ="select permission from roles_permissions where role_name in (:roles)";
        List<String> permissions = jdbc.query(sql, parameters, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String permission = resultSet.getString("permission");
                return permission;
            }
        });
        return permissions;
    }
}
