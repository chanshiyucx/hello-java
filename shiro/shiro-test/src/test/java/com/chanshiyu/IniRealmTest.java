package com.chanshiyu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testAuthentication() {
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        // 1.构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("shiyu", "123456");

        // 3.登录
        subject.login(token);
        System.out.println("isAuthentication:" + subject.isAuthenticated()); // true

        // 4.角色权限检查
        subject.checkRole("admin");
        subject.checkRoles("admin", "user");
        subject.checkPermission("user:delete");

        // 5.退出
        subject.logout();
        System.out.println("isAuthentication:" + subject.isAuthenticated()); // false
    }
}
