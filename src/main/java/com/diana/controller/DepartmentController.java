package com.diana.controller;

import com.diana.model.Department;
import com.diana.service.DepartmentService;
import com.diana.util.dto.DepRenameDTO;
import com.diana.util.error.EntityAlreadyExistsException;
import com.diana.util.validator.DepartmentDTOValidator;
import com.diana.util.validator.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = "com.diana.service")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentValidator departmentValidator;

    @Autowired
    private DepartmentDTOValidator departmentDTOValidator;

    @PostMapping("/newDepartment")
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestBody Department jsonDep, BindingResult result){
        departmentValidator.validate(jsonDep, result);
        departmentService.add(jsonDep);
    }

    @PutMapping("/renameDep")
    public String rename(@RequestBody DepRenameDTO dto, BindingResult result){
        departmentDTOValidator.validate(dto, result);
        return departmentService.rename(Long.parseLong(dto.getId()), dto.getName());
    }

}
