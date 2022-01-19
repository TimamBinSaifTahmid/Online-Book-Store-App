package database;

import profile.Profile;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

public class UserDatabase extends Database {


    public boolean addUser(String name, String email, String password, String phone, String address, String dob, int userType, String recommendation) {

        CallableStatement cst = null;
        try {
            cst = con.prepareCall("{call Add_value_User_table(?,?,?,?,?,?,?,?)}");
            cst.setString(1, name);
            cst.setString(2, email);
            cst.setString(3, password);
            cst.setString(4, phone);
            cst.setString(5, address);
            Date date = Date.valueOf(dob);
            cst.setDate(6, date);
            cst.setDouble(7, userType);
            cst.setString(8, recommendation);
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getUserId(String email, String password) {

        CallableStatement cst = null;
        double id = -1;
        try {
            cst = con.prepareCall("{call retrieve_id(?,?,?)}");
            cst.registerOutParameter(3, Types.DOUBLE);
            cst.setString(1, email);
            cst.setString(2, password);
            cst.execute();
            id = cst.getDouble(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Profile getProfileById(Double id) {
        Profile profile = null;
        CallableStatement statement;
        try {
            statement = con.prepareCall("{call User_profile(?,?,?,?,?,?,?,?)}");
            statement.setString(1, String.valueOf(id));
            statement.registerOutParameter(2, Types.VARCHAR);
            statement.registerOutParameter(3, Types.VARCHAR);
            statement.registerOutParameter(4, Types.VARCHAR);
            statement.registerOutParameter(5, Types.VARCHAR);
            statement.registerOutParameter(6, Types.VARCHAR);
            statement.registerOutParameter(7, Types.DATE);
            statement.registerOutParameter(8, Types.VARCHAR);
            statement.execute();

            profile = new Profile();
            profile.setName(statement.getString(2));
            profile.setEmail(statement.getString(3));
            profile.setPassword(statement.getString(4));
            profile.setPhone(statement.getString(5));
            profile.setAddress(statement.getString(6));
            profile.setDob(statement.getDate(7).toString());
            profile.setRecommendation(statement.getString(8));


            System.out.println(profile.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public boolean isAdmin(String email, String password) {
        CallableStatement statement;
        int value = 0;
        try {
            statement = con.prepareCall("{call ?:=check_Admin(?,?)}");
            statement.setString(2, email);
            statement.setString(3, password);
            statement.registerOutParameter(1, Types.DOUBLE);
            statement.execute();
            value = (int) statement.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value == 1;
    }
}
