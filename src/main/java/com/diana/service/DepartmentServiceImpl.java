package com.diana.service;

import com.diana.dao.DepartmentDAO;
import com.diana.model.Department;
import com.diana.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan(basePackages = "com.diana.dao")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Department findById(Long id) {
        return departmentDAO.findById(id);
    }


    @Override
    public List<Employee> getAllEmployeesByName(String name) {
        return getAllEmployeesByName(name);
    }

    @Override
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }

    @Override
    public void add(Department department) {
        departmentDAO.add(department);
    }

}
