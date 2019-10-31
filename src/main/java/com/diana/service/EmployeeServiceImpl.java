package com.diana.service;

import com.diana.dao.AddressDAO;
import com.diana.dao.DepartmentDAO;
import com.diana.dao.EmployeeDAO;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.util.dto.EmployeeDTO;
import com.diana.util.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan(basePackages = "com.diana.dao")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Employee findById(Long id) {
        Employee employee = employeeDAO.findById(id);
        if (employee == null){
            throw new EntityNotFoundException("Employee with such id does not exist");
        }
        return employee;
    }

    @Override
    public Employee findFetchedById(Long id) {
        Employee employee = employeeDAO.findFetchedById(id);
        if (employee == null){
            throw new EntityNotFoundException("Employee with such id does not exist");
        }
        return employee;
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
        addressDAO.add(address);
    }

    @Override
    public Employee findByPhone(String phone) {
        return employeeDAO.findByPhone(phone);
    }

    @Override
    public Employee findByName(String name) {
        return employeeDAO.findByName(name);
    }
}
