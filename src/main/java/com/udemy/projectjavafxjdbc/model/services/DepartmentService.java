package com.udemy.projectjavafxjdbc.model.services;

import com.udemy.projectjavafxjdbc.model.entites.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll(){
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1, "Books"));
        departmentList.add(new Department(2, "Computer"));
        departmentList.add(new Department(3, "Electronics"));
        return departmentList;
    }
}
