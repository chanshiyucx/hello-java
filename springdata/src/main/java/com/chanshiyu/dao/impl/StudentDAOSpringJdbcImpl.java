package com.chanshiyu.dao.impl;

import com.chanshiyu.dao.StudentDAO;
import com.chanshiyu.domain.Student;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDAOSpringJdbcImpl implements StudentDAO {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> query() {
        List<Student> students = new ArrayList<Student>();
        String sql = "select id, name, age from student";

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setAge(age);

                students.add(student);
            }
        });

        return students;
    }

    @Override
    public void save(Student student) {
        String sql ="insert into student(name, age) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{student.getName(), student.getAge()});
    }
}
