package com.chanshiyu.repository;

import com.chanshiyu.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeRepositoryTest {

    private ApplicationContext ctx;

    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeRepository = ctx.getBean(EmployeeRepository.class);
    }

    @After
    public void tearDown() {
        ctx = null;
    }

    @Test
    public void findByNameTest() {
        Employee employee = employeeRepository.findByName("shiyu");
        Assert.assertNotNull(employee);
    }

    @Test
    public void findByNameStartingWithAndAgeLessThanTest() {
        List<Employee> employees = employeeRepository.findByNameStartingWithAndAgeLessThan("shiyu", 22);
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void findByNameEndingWithAndAgeLessThanTest() {
        List<Employee> employees = employeeRepository.findByNameEndingWithAndAgeLessThan("1", 22);
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void findByNameInOrAgeLessThanTest() {
        List<String> names = new ArrayList<>();
        names.add("shiyu1");
        names.add("shiyu2");
        List<Employee> employees = employeeRepository.findByNameInOrAgeLessThan(names, 22);
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void getEmployeeByMaxIdTest() {
        Employee employee = employeeRepository.getEmployeeByMaxId();
        Assert.assertNotNull(employee);
    }

    @Test
    public void getEmployeeByNameAndAgeTest() {
        List<Employee> employees = employeeRepository.getEmployeeByNameAndAge2("chan1", 20);
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void getEmployeeByNameLikeTest() {
        List<Employee> employees = employeeRepository.getEmployeeByNameLike("shiyu");
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void getEmployeeByNameLike1Test() {
        List<Employee> employees = employeeRepository.getEmployeeByNameLike1("shiyu");
        Assert.assertNotEquals(0, employees.size());
    }

    @Test
    public void getCountTest() {
        long count = employeeRepository.getCount();
        Assert.assertNotEquals(0, count);
    }

}