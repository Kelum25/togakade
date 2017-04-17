/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thogakade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import thogakade.db.DBConnection;
import thogakade.model.Item;

/**
 *
 * @author linux
 */
public class ItemController {
    
    public static Item searchItem(String code) throws ClassNotFoundException, SQLException {
        String query = "select * from Item where code = ?";
        Connection connection = DBConnection.getDBConnection().getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(query);
        prepareStatement.setObject(1, code);
        ResultSet rst = prepareStatement.executeQuery();
        Item item = null;
        if (rst.next()) {
            item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand"));
        }
        return item;
    }
    
    public static boolean updateItemQty(ArrayList<Item> itemList) throws ClassNotFoundException, SQLException {
        int res = 0;
        for (Item item : itemList) {
            String query = "update Item set qtyOnHand = qtyOnHand - ? where code = ?";
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setObject(1, item.getQtyOnHand());     //Qty On Hand ==> Ordered Qty
            prepareStatement.setObject(2, item.getCode());
            res += prepareStatement.executeUpdate();
        }
        if (itemList.size() == res) {
            return true;
        }
        return false;
    }
    
    public static ArrayList<Item> getAllItems() throws ClassNotFoundException, SQLException{
        String query = "select * from Item";
        Connection connection = DBConnection.getDBConnection().getConnection();
        Statement createStatement = connection.createStatement();
        ResultSet rst = createStatement.executeQuery(query);
        ArrayList<Item> itemList = new ArrayList<>();
        while (rst.next()) {            
            Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand"));
            itemList.add(item);
        }
        return itemList;
    }
}
