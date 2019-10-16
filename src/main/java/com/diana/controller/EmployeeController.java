package com.diana.controller;

import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.service.AddressService;
import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import com.diana.util.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@ComponentScan(basePackages = "com.diana.service")
public class EmployeeController {

    //TODO: add delete option

    @Autowired
    private AddressService addressService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/newEmployee")
    @ResponseStatus(HttpStatus.OK)
    public void newEmp(@RequestBody EmployeeDTO jsonEmployee){
        employeeService.create(jsonEmployee);
    }

    @PostMapping("/employeeInfo{id}")
    public List<String> getInfo(@PathVariable("id") String _id){
        List<String> result = new ArrayList<>();

        Long id = Long.parseLong(_id);
        Employee e = employeeService.findFetchedById(id);
        result.add("Department: " + e.getDepartment().getName());
        Address a = addressService.findById(id);
        result.add("City: " + a.getCity());
        result.add("Street: " + a.getStreet());

        return result;
    }

}
