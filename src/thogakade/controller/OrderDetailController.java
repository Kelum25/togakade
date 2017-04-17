/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thogakade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import thogakade.db.DBConnection;
import thogakade.model.OrderDetail;

/**
 *
 * @author linux
 */
public class OrderDetailController {

    public static boolean addOrderDetail(ArrayList<OrderDetail> orderDetailList) throws ClassNotFoundException, SQLException {
        int res = 0;
        for (OrderDetail orderDetail : orderDetailList) {
            String query = "insert into OrderDetail values(?, ?, ?, ?)";
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setObject(1, orderDetail.getOrderId());
            prepareStatement.setObject(2, orderDetail.getItemCode());
            prepareStatement.setObject(3, orderDetail.getQty());
            prepareStatement.setObject(4, orderDetail.getUnitPrice());
            res += prepareStatement.executeUpdate();
        }
        if (orderDetailList.size() == res) {
            return true;
        }
        return false;
    }
}
