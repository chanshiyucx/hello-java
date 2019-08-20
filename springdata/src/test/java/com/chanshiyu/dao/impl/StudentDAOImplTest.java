package com.chanshiyu.dao.impl;

import com.chanshiyu.dao.StudentDAO;
import com.chanshiyu.domain.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StudentDAOImplTest {

    private final StudentDAO studentDAO = new StudentDAOImpl();

    @Test
    public void queryTest() {
        List<Student> students = studentDAO.query();
        Assert.assertNotEquals(0, students.size());
    }

    @Test
    public void saveTest() {
        Student student = new Student();
        student.setName("shiyu");
        student.setAge(26);
        studentDAO.save(student);
    }
}