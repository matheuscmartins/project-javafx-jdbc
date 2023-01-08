package com.udemy.projectjavafxjdbc.model.dao.impl;

import com.udemy.projectjavafxjdbc.Db.DB;
import com.udemy.projectjavafxjdbc.Db.DbException;
import com.udemy.projectjavafxjdbc.Db.DbIntegrityException;
import com.udemy.projectjavafxjdbc.model.dao.DepartmentDao;
import com.udemy.projectjavafxjdbc.model.entites.Department;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection connection;

    public DepartmentDaoJDBC(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void insert(Department department) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, department.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    department.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }

    }
    @Override
    public void update(Department department) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE Department "
                            + "SET Name = ? "
                            + "WHERE Id = ?");

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }
    @Override
    public void deleteById(Department department) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement= connection.prepareStatement("DELETE FROM department WHERE Id = ?");
            preparedStatement.setInt(1,department.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }
    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * "
                            + "FROM Department  "
                            + "WHERE Id = ?"
            );
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                Department objDepartment = instantiateDepartment(resultSet);

                return objDepartment;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }
    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * "
                            + "FROM department "
                            + "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();
            List<Department> list = new ArrayList<>();

            while (resultSet.next()) {            //enquanto o ResultSet tiver linha

                Department objDepartment = instantiateDepartment(resultSet);
                list.add(objDepartment);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department objDepartment = new Department();
        objDepartment.setId(resultSet.getInt("Id"));
        objDepartment.setName(resultSet.getString("Name"));
        return objDepartment;
    }

    @Override
    public List<Department> findLikeName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * "
                            + "FROM department "
                            + "WHERE Name LIKE  CONCAT( '%',?,'%') "
                            + "ORDER BY Name"
            );
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            List<Department> list = new ArrayList<>();

            while (resultSet.next()) {

                Department objDepartment = instantiateDepartment(resultSet);
                list.add(objDepartment);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
