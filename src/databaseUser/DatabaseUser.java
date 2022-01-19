package databaseUser;

import static databaseUser.UserType.CUSTOMER;

public class DatabaseUser {
    private static double userId;
    public static String userType = CUSTOMER;


    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        DatabaseUser.userType = userType;
    }

    public static double getUserId() {
        return userId;
    }

    public static void setUserId(double userId) {
        DatabaseUser.userId = userId;
    }
}
