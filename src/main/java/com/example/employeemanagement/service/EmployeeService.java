package com.example.employeemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

  //一覧表示（ページング対応）
    public Page<Employee> findAll(int page, String sort) {

        Sort sortOrder = createSort(sort);

        Pageable pageable = PageRequest.of(page, 10, sortOrder);

        return employeeRepository.findAll(pageable);

    }
    
    public Page<Employee> searchByName(
            String name,
            int page,
            String sort) {

        Sort sortOrder = createSort(sort);

        Pageable pageable =
                PageRequest.of(page, 10, sortOrder);

        return employeeRepository.findByNameContaining(
                name,
                pageable);

    }
    
    private Sort createSort(String sort) {

        switch (sort) {

            case "nameAsc":
                return Sort.by("name").ascending();

            case "nameDesc":
                return Sort.by("name").descending();

            case "ageAsc":
                return Sort.by("age").ascending();

            case "ageDesc":
                return Sort.by("age").descending();

            default:
                return Sort.by("id").ascending();
        }
    }
    
    //編集→保存
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
    
    //検索
    public Employee findById(Integer id) {

        return employeeRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("社員が存在しません。"));

    }
    
    //削除
    public void delete(Integer id) {
    	
        employeeRepository.deleteById(id);
        
    }
    
    
}