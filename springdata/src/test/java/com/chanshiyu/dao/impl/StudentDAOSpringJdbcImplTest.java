package com.chanshiyu.dao.impl;

import com.chanshiyu.dao.StudentDAO;
import com.chanshiyu.domain.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class StudentDAOSpringJdbcImplTest {

    private ApplicationContext ctx;

    private  StudentDAO studentDAO;

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans.xml");
        studentDAO = (StudentDAO) ctx.getBean("studentDAO");
    }

    @After
    public void tearDown() {
        ctx = null;
    }

    @Test
    public void queryTest() {
        List<Student> students = studentDAO.query();
        Assert.assertNotEquals(0, students.size());
    }

    @Test
    public void saveTest() {
        Student student = new Student();
        student.setName("chan");
        student.setAge(20);
        studentDAO.save(student);
    }
}