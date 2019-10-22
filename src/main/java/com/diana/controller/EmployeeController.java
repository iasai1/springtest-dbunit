package com.diana.controller;

import com.diana.model.Address;
import com.diana.model.Employee;
import com.diana.service.AddressService;
import com.diana.service.EmployeeService;
import com.diana.util.dto.EmployeeDTO;
import com.diana.util.dto.EmployeeInfoDTO;
import com.diana.util.validator.EmployeeDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = "com.diana.service")
public class EmployeeController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDTOValidator employeeDTOValidator;

    static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/newEmployee")
    @ResponseStatus(HttpStatus.OK)
    public void newEmp(@RequestBody EmployeeDTO jsonEmployee, BindingResult result){
        employeeDTOValidator.validate(jsonEmployee, result);
        employeeService.create(jsonEmployee);
    }

    @PostMapping("/employeeInfo{id}")
    public EmployeeInfoDTO getInfo(@PathVariable("id") String _id){
        EmployeeInfoDTO response = new EmployeeInfoDTO();

        Long id = Long.parseLong(_id);
        Employee e = employeeService.findFetchedById(id);
        response.setDepName(e.getDepartment().getName());
        Address a = addressService.findById(id);
        response.setCity(a.getCity());
        response.setStreet(a.getStreet());

        return response;
    }

}
