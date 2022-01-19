package database;

import com.sun.deploy.security.ValidationState;
import com.sun.xml.internal.ws.org.objectweb.asm.Type;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;
import order.CurrentOrder;
import order.Order;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabase extends Database {

    public boolean addOrder(Long Book_id, Double U_id, Double amount) {
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call add_To_Orders(?,?,?)}");
            statement.setDouble(1, (double) Book_id);
            statement.setDouble(2, U_id);
            statement.setDouble(3, amount);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=order_list}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.execute();
            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                Long id = (long) rs.getDouble(1);
                Long bId = (long) rs.getDouble(2);
                Long userId = (long) rs.getDouble(3);
                Double amount = rs.getDouble(4);
                Double price = rs.getDouble(5);
                String date = rs.getDate(6).toString();
                Double dueAmount = rs.getDouble(7);
                Double paidAmount = rs.getDouble(8);
                String bookName = rs.getString(9);
                String userName = rs.getString(10);
                orders.add(
                        new Order(
                                id,
                                bId,
                                userId,
                                bookName,
                                userName,
                                amount,
                                price,
                                date,
                                dueAmount,
                                paidAmount
                        )
                );
            }

            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getDueOrders() {
        List<Order> orders = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=order_list_due}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);

            statement.execute();
            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                Long id = (long) rs.getDouble(1);
                Long bId = (long) rs.getDouble(2);
                Long userId = (long) rs.getDouble(3);
                Double amount = rs.getDouble(4);
                Double price = rs.getDouble(5);
                String date = rs.getDate(6).toString();
                Double dueAmount = rs.getDouble(7);
                Double paidAmount = rs.getDouble(8);
                String bookName = rs.getString(9);
                String userName = rs.getString(10);

                orders.add(
                        new Order(
                                id,
                                bId,
                                userId,
                                bookName,
                                userName,
                                amount,
                                price,
                                date,
                                dueAmount,
                                paidAmount
                        )
                );
            }

            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;

    }

    public boolean isDueOrder(Long order_id) {
        CallableStatement statement;
        int res;
        try {
            statement = con.prepareCall("{call ?:= due_order_checker(?)}");
            statement.registerOutParameter(1, Type.DOUBLE);
            statement.setDouble(2, (double) CurrentOrder.getOrder().getId());
            statement.execute();
            res = (int) statement.getDouble(1);
            System.out.println("success2");
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateOrder(Long order_id) {
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call update_Order(?)}");
            statement.setDouble(1,order_id);
            statement.execute();

            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
