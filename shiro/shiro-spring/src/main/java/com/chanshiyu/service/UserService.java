package com.chanshiyu.service;

import com.chanshiyu.vo.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getUserByUsername(String username);

    List<String> getRoleByUsername(String username);

    List<String> getPermissionByRole(Set<String> roles);
}
