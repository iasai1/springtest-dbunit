package com.diana.dao;

import com.diana.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    Employee findById(Long id);

    Employee findFetchedById(Long id);

    Employee findByName(String name);

    List<Employee> getAll();

    void add(Employee employee);

    void delete(Employee employee);



}
