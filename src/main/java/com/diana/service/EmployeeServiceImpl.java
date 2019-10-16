package com.diana.service;

import com.diana.dao.DepartmentDAO;
import com.diana.dao.EmployeeDAO;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.util.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@ComponentScan(basePackages = "com.diana.dao")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Employee findById(Long id) {
        return employeeDAO.findById(id);
    }

    @Override
    public Employee findFetchedById(Long id) {
        return employeeDAO.findFetchedById(id);
    }

    @Override
    public void add(Employee employee) {
        employeeDAO.add(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    @Override
    public void create(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.getEmployee();
        Department d = departmentDAO.findByName(employeeDTO.getDepName());
        d.addEmployee(employee);
        departmentDAO.add(d);
        Address address = employeeDTO.getAddress();
        address.setEmployee(employee);
        addressService.add(address);
    }
}
