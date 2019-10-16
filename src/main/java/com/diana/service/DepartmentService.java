package com.diana.service;

import com.diana.model.Department;
import com.diana.model.Employee;

import java.util.List;

public interface DepartmentService {

    Department findById(Long id);

    List<Employee> getAllEmployeesByName(String name);

    List<Department> getAll();

    void add(Department department);

}
