/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thogakade.other;

import java.sql.SQLException;
import java.text.NumberFormat;
import thogakade.controller.IdController;

/**
 *
 * @author linux
 */
public class IDGenerator {
    
    public static String getNewId(String tableName, String columnName, String prefix) throws ClassNotFoundException, SQLException {
        String lastId = IdController.getLastId(tableName, columnName);
        if (lastId != null) {
            int id = Integer.parseInt(lastId.split(prefix)[1]);
            id++;
            NumberFormat numberFormat = NumberFormat.getIntegerInstance();
            numberFormat.setMaximumIntegerDigits(3);
            numberFormat.setGroupingUsed(false);
            String newID = numberFormat.format(id);
            return prefix + newID;
        } else {
            return prefix + "001";
        }
    }
}
