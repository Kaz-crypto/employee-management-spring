package com.example.employeemanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //一覧表示
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    public List<Employee> searchByName(String name) {

        return employeeRepository.findByNameContaining(name);

    }
    
    //編集→保存
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
    
    //検索
    public Employee findById(Integer id) {

        return employeeRepository.findById(id).orElse(null);

    }
    
    //削除
    public void deleteById(Integer id) {

        employeeRepository.deleteById(id);

    }
    
    
}