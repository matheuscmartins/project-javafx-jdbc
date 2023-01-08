package com.udemy.projectjavafxjdbc.Db;

public class DbException  extends RuntimeException{
    private static  final long serialVersionUID = 1l;
    public DbException(String msg){
        super(msg);
    }
}
