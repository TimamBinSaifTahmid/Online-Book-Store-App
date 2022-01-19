package database;

import book.Book;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;
import order.Order;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDatabase extends Database {
    public List<Book> pendingRating(Double userId) {
        List<Book> books = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=pending_rating(?)}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setDouble(2, userId);
            statement.execute();
            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                Long id = (long) rs.getDouble(1);
                String name = rs.getString(2);
                int quantity = (int) rs.getDouble(3);
                Double price = rs.getDouble(4);
                String description = rs.getString(5);
                String status = rs.getString(6);
                Double rating = rs.getDouble(7);
                System.out.println("ami handaisi");
                books.add(
                        new Book(
                                id,
                                name,
                                quantity,
                                price,
                                description,
                                status,
                                rating
                        )
                );
                System.out.println(books.get(books.size() - 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void rateBook(long book_Id, long user_Id, Double rating) {

        CallableStatement statement;
        System.out.println(book_Id + " " + user_Id + " " + rating);
        try {
            statement = con.prepareCall("{call give_rate(?,?,?)}");
            statement.setDouble(1, (double) book_Id);
            statement.setDouble(2, (double) user_Id);
            statement.setDouble(3, rating);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
