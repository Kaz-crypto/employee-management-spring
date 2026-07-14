package com.example.employeemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagement.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByNameContaining(String name);
}