package com.udemy.projectjavafxjdbc.model.dao.impl;


import com.udemy.projectjavafxjdbc.Db.DB;
import com.udemy.projectjavafxjdbc.Db.DbException;
import com.udemy.projectjavafxjdbc.model.dao.SellerDao;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.entites.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    seller.setId(id);
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
    public void update(Seller seller) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Seller seller) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement= connection.prepareStatement("DELETE FROM seller WHERE Id = ?");
            preparedStatement.setInt(1,seller.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?"
            );
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                Department objDepartment = instantiateDepartment(resultSet);
                Seller objSeller = instantiateSeller(resultSet, objDepartment);
                return objSeller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

    }

    private Seller instantiateSeller(ResultSet resultSet, Department objDepartment) throws SQLException {
        Seller objSeller = new Seller();
        objSeller.setId(resultSet.getInt("Id"));
        objSeller.setName(resultSet.getString("Name"));
        objSeller.setEmail(resultSet.getString("Email"));
        objSeller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        objSeller.setBirthDate(resultSet.getDate("BirthDate"));
        objSeller.setDepartment(objDepartment);
        return objSeller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department objDepartment = new Department();
        objDepartment.setId(resultSet.getInt("DepartmentId"));
        objDepartment.setName(resultSet.getString("DepName"));
        return objDepartment;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {            //enquanto o ResultSet tiver linha

                Department objDepartment = map.get(resultSet.getInt("DepartmentId"));
                //obtem os Department que estão no map sejam eles existentes ou null

                if (objDepartment == null) {        //se o o Department for null

                    objDepartment = instantiateDepartment(resultSet);   //função para obter um Department do banco

                    map.put(resultSet.getInt("DepartmentId"), objDepartment);
                    //adiciona Department que não existe no map para ser reutilizado

                }

                Seller objSeller = instantiateSeller(resultSet, objDepartment);
                list.add(objSeller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {            //enquanto o ResultSet tiver linha

                Department objDepartment = map.get(resultSet.getInt("DepartmentId"));
                //obtem os Department que estão no map sejam eles existentes ou null

                if (objDepartment == null) {        //se o o Department for null

                    objDepartment = instantiateDepartment(resultSet);   //função para obter um Department do banco

                    map.put(resultSet.getInt("DepartmentId"), objDepartment);
                    //adiciona Department que não existe no map para ser reutilizado

                }

                Seller objSeller = instantiateSeller(resultSet, objDepartment);
                list.add(objSeller);
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
