package com.example.employeemanagement.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmployeeForm {

    private Integer id;

    @NotBlank(message = "名前を入力してください。")
    private String name;

    @Min(value = 0, message = "年齢は0以上を入力してください。")
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}