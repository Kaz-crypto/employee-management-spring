package com.example.employeemanagement.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.form.EmployeeForm;
import com.example.employeemanagement.service.EmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //一覧表示
    @GetMapping("/employees")
    public String employeeList(
            @RequestParam(required = false) String name,
            Model model) {

        if (name == null || name.isBlank()) {
            model.addAttribute("employeeList", employeeService.findAll());
        } else {
            model.addAttribute("employeeList", employeeService.searchByName(name));
        }

        model.addAttribute("name", name);

        return "employee-list";
    }
    
    //登録
    @GetMapping("/employees/new")
    public String newEmployee(Model model) {

    	model.addAttribute("employee", new EmployeeForm());

        return "employee-form";
    }
    
    //保存
    @PostMapping("/employees")
    public String saveEmployee(
            @Valid Employee employee,
            BindingResult result) {

        if(result.hasErrors()) {
            return "employee-form";
        }

        employeeService.save(employee);

        return "redirect:/employees";
    }
    
    //更新
    @GetMapping("/employees/{id}/edit")
    public String editEmployee(@PathVariable Integer id,
                               Model model) {

        Employee employee = employeeService.findById(id);

        model.addAttribute("employee", employee);

        return "employee-form";
    }
    
    //削除
    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable Integer id) {

        employeeService.deleteById(id);

        return "redirect:/employees";
    }
    
    
}