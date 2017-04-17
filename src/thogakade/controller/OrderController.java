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
import thogakade.model.Item;
import thogakade.model.Order;
import thogakade.model.OrderDetail;

/**
 *
 * @author linux
 */
public class OrderController {

    public static boolean addOrder(Order order, ArrayList<OrderDetail> orderDetailList, ArrayList<Item> itemList) throws ClassNotFoundException, SQLException {
        String query = "insert into Orders values(?, ?, ?)";
        Connection connection = DBConnection.getDBConnection().getConnection();
        connection.setAutoCommit(false);
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setObject(1, order.getId());
            prepareStatement.setObject(2, order.getDate());
            prepareStatement.setObject(3, order.getCustomerId());
            int res = prepareStatement.executeUpdate();
            if (res > 0) {
                boolean addOrderDetail = OrderDetailController.addOrderDetail(orderDetailList);
                if (addOrderDetail) {
                    boolean updateItemQty = ItemController.updateItemQty(itemList);
                    if (updateItemQty) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            }
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
