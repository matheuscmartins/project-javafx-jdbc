package com.udemy.projectjavafxjdbc.model.dao;

import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.entites.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Seller seller);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
    
}
