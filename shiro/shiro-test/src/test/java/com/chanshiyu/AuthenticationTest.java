package com.chanshiyu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("shiyu", "123456", "admin", "user");
    }

    /**
     * 认证与授权
     */
    @Test
    public void testAuthentication() {
        // 1.构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("shiyu", "123456");

        // 3.登录
        subject.login(token);
        System.out.println("isAuthentication:" + subject.isAuthenticated()); // true

        // 4.角色检查
        subject.checkRole("admin");
        subject.checkRoles("admin", "user");

        // 5.退出
        subject.logout();
        System.out.println("isAuthentication:" + subject.isAuthenticated()); // false
    }
}
