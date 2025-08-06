package com.mycompany.tennis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDeConnection {
    public static void main(String... args){
        Connection conn = null;
        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris","JDBC","javacore");

            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}