package com.chanshiyu.repository;

import com.chanshiyu.domain.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends Repository<Employee, Integer> {

    public Employee findByName(String name);

    // where name like ?% and age <?
    public List<Employee> findByNameStartingWithAndAgeLessThan(String name, Integer age);

    // where name like %? and age <?
    public List<Employee> findByNameEndingWithAndAgeLessThan(String name, Integer age);

    // where name in (?, ?...) or age <?
    public List<Employee> findByNameInOrAgeLessThan(List<String> names, Integer age);

    @Query("select o from Employee o where id=(select max(id) from Employee t1)")
    public Employee getEmployeeByMaxId();

    @Query("select o from Employee o where o.name=?1 and o.age=?2")
    public List<Employee> getEmployeeByNameAndAge(String name, Integer age);

    @Query("select o from Employee o where o.name=:name and o.age=:age")
    public List<Employee> getEmployeeByNameAndAge2(@Param("name")String name, @Param("age")Integer age);

    @Query("select o from Employee o where o.name like %?1%")
    public List<Employee> getEmployeeByNameLike(String name);

    @Query("select o from Employee o where o.name like %:name%")
    public List<Employee> getEmployeeByNameLike1(@Param("name")String name);

    // 查询记录总数，这里 employee 是表名
    @Query(nativeQuery = true, value = "select count(1) from employee")
    public long getCount();

    @Modifying
    @Query("update Employee o set o.age = :age where o.id = :id")
    public void updateAgeById(@Param("id")Integer id,@Param("age")Integer age);
}

