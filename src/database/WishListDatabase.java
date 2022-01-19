package database;

import book.Book;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class WishListDatabase extends Database {

    public boolean addToWishList(Long Book_id, Double user_id) {
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call add_to_Wishlist(?,?)}");
            statement.setDouble(1, (double) Book_id);
            statement.setDouble(2, user_id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isWishListExist(Long Book_id, Double user_id) {
        CallableStatement statement;
        int count = 0;
        try {
            statement = con.prepareCall("{call ?:=Wishlist_exists(?,?)}");
            statement.registerOutParameter(1, Types.DOUBLE);
            statement.setDouble(2, (double) Book_id);
            statement.setDouble(3, user_id);
            statement.execute();
            count = (int) statement.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
     
}
