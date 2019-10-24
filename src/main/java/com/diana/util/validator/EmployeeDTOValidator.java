package com.diana.util.validator;

import com.diana.model.Employee;
import com.diana.service.EmployeeService;
import com.diana.util.dto.EmployeeDTO;
import com.diana.util.error.EntityAlreadyExistsException;
import com.diana.util.error.MalformedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@ComponentScan(basePackages = {"com.diana.service"})
public class EmployeeDTOValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(EmployeeDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmployeeDTO dto = (EmployeeDTO) o;

        if (!dto.getName().matches("^[a-zA-Z]+$")){
            throw new MalformedInputException("Employee's name should only contain letters");
        }
        if (!dto.getPhone().matches("^[0-9]+$")){
            throw new MalformedInputException("Employee's phone should only contain numbers");
        }

        Employee employee = employeeService.findByName(dto.getName());
        if (employee != null ){
            throw new EntityAlreadyExistsException("Employee with such name already exists");
        }
        employee = employeeService.findByPhone(dto.getPhone());
        if (employee != null){
            throw new EntityAlreadyExistsException("Employee with such phone already exists");
        }

    }
}
