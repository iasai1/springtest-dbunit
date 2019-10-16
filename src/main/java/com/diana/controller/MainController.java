package com.diana.controller;


import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ComponentScan(basePackages = "com.diana.service")
public class MainController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String index(){
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String showEmployees(Model model){
        model.addAttribute("employees", employeeService.getAll());
        return "/employees";
    }

    @GetMapping("/newDepartment")
    public String showNewDep(){
        return "/newDep";
    }

    @GetMapping("newEmployee")
    public String showNewEmp(Model model){
        model.addAttribute("departments", departmentService.getAll());
        return "/newEmployee";
    }
}
