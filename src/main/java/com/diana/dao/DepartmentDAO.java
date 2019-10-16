package com.diana.dao;

import com.diana.model.Department;

import java.util.List;

public interface DepartmentDAO {

    Department findById(Long id);

    Department findByName(String name);

    List<Department> getAll();

    void add(Department department);


}
