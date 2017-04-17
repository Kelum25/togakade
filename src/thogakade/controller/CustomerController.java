/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thogakade.controller;

import thogakade.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import thogakade.db.DBConnection;

/**
 *
 * @author linux
 */
public class CustomerController {

    public static int addCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        String sql = "Insert into Customer values(?,?,?,?)";
        Connection conn = DBConnection.getDBConnection().getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setObject(1, customer.getId());
        stm.setObject(2, customer.getName());
        stm.setObject(3, customer.getAddress());
        stm.setObject(4, customer.getSalary());
        return  stm.executeUpdate();
        
    }
    public static int updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        String sql = "Update Customer set name=?,address=?,salary=? where id=?";
        Connection conn = DBConnection.getDBConnection().getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setObject(4, customer.getId());
        stm.setObject(1, customer.getName());
        stm.setObject(2, customer.getAddress());
        stm.setObject(3, customer.getSalary());
        return  stm.executeUpdate();
        
    }    
    public static int deleteCustomer(String id) throws ClassNotFoundException, SQLException {
        String sql = "Delete From Customer where id=?";
        Connection conn = DBConnection.getDBConnection().getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setObject(1, id);
        return  stm.executeUpdate();
    }        
    public static Customer searchCustomer(String id) throws ClassNotFoundException, SQLException {
        String sql = "Select * From Customer where id=?";
        Connection conn = DBConnection.getDBConnection().getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if(rst.next()){
            Customer customer=new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
            return customer;
        }else{
            return null;
        }
    }
    public static ArrayList<Customer> getAllCustomer() throws ClassNotFoundException, SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        ResultSet rst=stm.executeQuery("Select * From Customer");
        ArrayList<Customer>customerList=new ArrayList<>();
        while(rst.next()){
            Customer customer=new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
            customerList.add(customer);
        }
        return customerList;
    }
}
