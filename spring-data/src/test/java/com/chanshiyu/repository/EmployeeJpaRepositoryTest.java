package com.chanshiyu.repository;

import com.chanshiyu.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Slf4j
public class EmployeeJpaRepositoryTest {

    private ApplicationContext ctx;

    private EmployeeJpaRepository employeeJpaRepository;

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaRepository = ctx.getBean(EmployeeJpaRepository.class);
    }

    @After
    public void tearDown() {
        ctx = null;
    }

    @Test
    public void sortTest() {

        Specification<Employee> specification = new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("age");
                return criteriaBuilder.gt(path, 22);
            }
        };

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(0, 10, sort);
        Page<Employee> employeePage = employeeJpaRepository.findAll(specification, pageRequest);

        log.info("{}", employeePage.getContent());
    }
}