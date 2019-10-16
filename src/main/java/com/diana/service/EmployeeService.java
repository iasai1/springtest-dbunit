package com.diana.service;

import com.diana.model.Address;
import com.diana.model.Employee;
import com.diana.util.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    Employee findById(Long id);

    Employee findFetchedById(Long id);

    List<Employee> getAll();

    void create(EmployeeDTO employeeDTO);

    void add(Employee employee);


}
