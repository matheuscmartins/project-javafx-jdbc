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

    public void saveOrUpdate(Department department){
        if (department.getId() == null){
            dao.insert(department);
        }
        else {
            dao.update(department);
        }
    }
    public void remove(Department department){
        dao.deleteById(department);
    }
}
