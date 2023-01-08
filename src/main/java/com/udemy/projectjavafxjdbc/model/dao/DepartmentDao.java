package com.udemy.projectjavafxjdbc.model.dao;

import com.udemy.projectjavafxjdbc.model.entites.Department;


import java.util.List;

public interface DepartmentDao {

    void insert(Department department);
    void update(Department department);
    void deleteById(Department department);
    Department findById(Integer id);
    List<Department> findAll();
    List<Department> findLikeName(String name);
}
