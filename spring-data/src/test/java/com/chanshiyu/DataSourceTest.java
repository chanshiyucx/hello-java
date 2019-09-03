package com.chanshiyu;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DataSourceTest {

    private ApplicationContext ctx;

    @Before
    public void setup() {
        System.out.println("setup");
        ctx = new ClassPathXmlApplicationContext("beans.xml");
    }

    @After
    public void tearDown() {
        System.out.println("teardown");
        ctx = null;
    }

    @Test
    public void dataSourceTest() {
        System.out.println("dataSourceTest");
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void jdbcTemplateTest() {
        System.out.println("jdbcTemplateTest");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        Assert.assertNotNull(jdbcTemplate);
    }
}
