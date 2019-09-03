package com.chanshiyu.service;

import com.chanshiyu.domain.Employee;
import com.chanshiyu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void updateAgeById(Integer id, Integer age) {
        employeeRepository.updateAgeById(id, age);
    }
}
