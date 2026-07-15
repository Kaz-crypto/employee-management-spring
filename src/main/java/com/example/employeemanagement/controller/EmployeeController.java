package com.example.employeemanagement.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sort,
            Model model) {

        if (name == null || name.isBlank()) {
        	model.addAttribute(
        		    "employeePage",
        		    employeeService.findAll(page, sort));
        } else {
        	model.addAttribute(
        		    "employeePage",
        		    employeeService.searchByName(name, page, sort));
        }

        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

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
            BindingResult result,
            RedirectAttributes redirectAttributes) {

    	//新しいか判定する変数
    	boolean isNew = (employee.getId() == null);
    	
        if (result.hasErrors()) {
            return "employee-form";
        }

        employeeService.save(employee);

        if (isNew) {
            redirectAttributes.addFlashAttribute(
                "message",
                "社員を登録しました。");
        } else {
            redirectAttributes.addFlashAttribute(
                "message",
                "社員情報を更新しました。");
        }

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
    public String deleteEmployee(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        employeeService.delete(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "社員を削除しました。");

        return "redirect:/employees";
    }
    
    //例外処理
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            RuntimeException e,
            Model model) {

        model.addAttribute("message", e.getMessage());

        return "error";
    }
    
}