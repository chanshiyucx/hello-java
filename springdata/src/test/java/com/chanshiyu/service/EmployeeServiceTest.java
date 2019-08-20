package com.chanshiyu.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmployeeServiceTest {

    private ApplicationContext ctx;

    private EmployeeService employeeService;

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeService = ctx.getBean(EmployeeService.class);
    }

    @After
    public void tearDown() {
        ctx = null;
    }

    @Test
    public void updateAgeByIdTest() {
        employeeService.updateAgeById(1, 33);
    }
}