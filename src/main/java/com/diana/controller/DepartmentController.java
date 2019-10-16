package com.diana.controller;

import com.diana.model.Department;
import com.diana.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "com.diana.service")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/newDepartment")
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestBody Department jsonDep){
        departmentService.add(jsonDep);
    }

}
