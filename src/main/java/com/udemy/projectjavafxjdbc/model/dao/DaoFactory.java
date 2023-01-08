package com.udemy.projectjavafxjdbc.model.dao;

import com.udemy.projectjavafxjdbc.Db.DB;
import com.udemy.projectjavafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import com.udemy.projectjavafxjdbc.model.dao.impl.SellerDaoJDBC;


public class DaoFactory {

    public  static  SellerDao createSellerDao(){
        return  new SellerDaoJDBC(DB.getConnection());
    }
    public  static  DepartmentDao createDepartmentDao(){
        return  new DepartmentDaoJDBC(DB.getConnection());
    }
}
