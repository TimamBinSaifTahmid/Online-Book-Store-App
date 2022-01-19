package register;

import database.Database;
import database.UserDatabase;

public class Register {
    private double userId;
    private UserDatabase database = new UserDatabase();


    public Register() {
        database.connect();
    }

    public double getUserId() {
        return userId;
    }

    public boolean isValidUser(String email, String password) {
        userId = database.getUserId(email, password);
        return userId != -1;
    }

    public boolean isAdmin(String email, String password) {
        return database.isAdmin(email, password);
    }
}
