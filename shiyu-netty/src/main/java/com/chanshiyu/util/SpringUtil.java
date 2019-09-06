package com.chanshiyu.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author shiyu
 * @date 2019/9/6 18:19
 * @Description: 提供手动获取被 Spring 管理的 bean 对象
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    // 获取 applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过 name 获取 Bean
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过 class 获取 Bean
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过 name 以及 Clazz 返回指定的 Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
