package com.diana.service;

import com.diana.model.Employee;
import com.diana.util.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    Employee findById(Long id);

    Employee findFetchedById(Long id);

    Employee findByName(String name);

    Employee findByPhone(String phone);

    List<Employee> getAll();

    void create(EmployeeDTO employeeDTO);

    void add(Employee employee);

}
