package database;

import book.Book;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;
import profile.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    protected Connection con;
    protected Statement statement;

    public void connect() {
        String username = "OBS";
        String password = "obs";
        String url = "jdbc:oracle:thin:@127.0.0.1:1521/XE";
        String driver = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, username, password);
            statement = con.createStatement();
            System.out.println("connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    } 
}