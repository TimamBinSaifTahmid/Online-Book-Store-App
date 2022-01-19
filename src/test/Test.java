package test;


import java.sql.*;
public class Test {

    public static void main(String[] args) {
        String username="tahmid";
        String password="tahmid";
        String url="jdbc:oracle:thin:@127.0.0.1:1521/XE";
        String driver="oracle.jdbc.driver.OracleDriver";


        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection(url,username,password);
/*            CallableStatement cst=con.prepareCall("{Add_value_User_table(?,?,?,?,?,?,?,?)}");

            cst.setString(1,"lol");
            cst.setString(2,"lol@gmail.com");
            cst.setString(3,"123456");
            cst.setString(4,"0181771543");
           cst.setString(5,"Farmgate");
           Date date=Date.valueOf("1897-05-30");
           cst.setDate(6, date);
           cst.setDouble(7,1);
           cst.setString(8,"");*/

            CallableStatement cst=con.prepareCall("{call retrieve_id(?,?,?)}");
            cst.registerOutParameter(3, Types.DOUBLE);
            cst.setString(1,"rafi");
            cst.setString(2,"123456");
            cst.execute();
            Double ca= cst.getDouble(3);
            System.out.println(ca);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }




}
