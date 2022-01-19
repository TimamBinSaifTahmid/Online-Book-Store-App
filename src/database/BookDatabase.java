package database;

import book.Book;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BookDatabase extends Database {

    public Book getBookByName(String name) {
        Book book = null;
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call search_book(?,?,?,?,?,?,?,?)}");
            statement.setString(1, name);
            statement.registerOutParameter(2, Types.VARCHAR);
            statement.registerOutParameter(3, Types.DOUBLE);
            statement.registerOutParameter(4, Types.DOUBLE);
            statement.registerOutParameter(5, Types.VARCHAR);
            statement.registerOutParameter(6, Types.VARCHAR);
            statement.registerOutParameter(7, Types.DOUBLE);
            statement.registerOutParameter(8, Types.DOUBLE);
            statement.execute();

            book = new Book();
            book.setName(statement.getString(2));
            book.setQuantity((long) statement.getDouble(3));
            book.setPrice(statement.getDouble(4));
            book.setDescription(statement.getString(5));
            book.setStatus(statement.getString(6));
            book.setRating(statement.getDouble(7));
            book.setId((long) statement.getDouble(8));

            System.out.println(book.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> getBookByCategory(String category) {
        List<Book> books = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=category_search_book(?)}");
            statement.setString(2, category);
            statement.registerOutParameter(1, OracleTypes.CURSOR);

            statement.execute();

            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                Book book = new Book();
                book.setName(rs.getString(1));
                book.setQuantity((long) rs.getDouble(2));
                book.setPrice(rs.getDouble(3));
                book.setDescription(rs.getString(4));
                book.setStatus(rs.getString(5));
                book.setRating(rs.getDouble(6));
                book.setId((long) rs.getDouble(7));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void add_new_books(String category_name, String b_name, Double quantity, Double price, String description, String status, Double rating) {
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call add_new_books(?,?,?,?,?,?,?)}");
            statement.setString(1, category_name);
            statement.setString(2, b_name);
            statement.setDouble(3, quantity);
            statement.setDouble(4, price);
            statement.setString(5, description);
            statement.setString(6, status);
            statement.setDouble(7, rating);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addNewCatagory(String category_name) {
        CallableStatement statement;
        try {

            statement = con.prepareCall("{call add_new_catagory(?)}");
            statement.setString(1, category_name);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=get_All_BookList}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);

            statement.execute();
            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                books.add(
                        new Book(
                                (long) rs.getDouble(7),
                                rs.getString(1),
                                (long) rs.getDouble(2),
                                rs.getDouble(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDouble(6)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getWishListBooks(Double userId) {
        List<Book> books = new ArrayList<>();
        CallableStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareCall("{call ?:=show_wishlist(?)}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setDouble(2, userId);
            statement.execute();
            rs = ((OracleCallableStatement) statement).getCursor(1);
            while (rs.next()) {
                Long id = (long) rs.getDouble(1);
                String name = rs.getString(2);
                Long quantity = (long) rs.getDouble(3);
                Double price = rs.getDouble(4);
                String description = rs.getString(5);
                String status = rs.getString(6);
                Double rating = rs.getDouble(7);

                while (rs.next()) {
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
                }

            }

            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
