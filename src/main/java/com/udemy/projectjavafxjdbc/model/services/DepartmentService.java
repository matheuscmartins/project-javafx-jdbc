package com.udemy.projectjavafxjdbc.model.services;

import com.udemy.projectjavafxjdbc.model.dao.DaoFactory;
import com.udemy.projectjavafxjdbc.model.dao.DepartmentDao;
import com.udemy.projectjavafxjdbc.model.entites.Department;


import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll(){
        return dao.findAll();

    }
}
