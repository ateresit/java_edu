package com.itia.cs.server.handlers;

import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static PreparedStatement psUserDesktop;

    public static boolean connectDB(){
        System.out.println("check connect to DB");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:cloud_storage.db");
            System.out.println("DB connected OK!");
            preparedAllStatements();
            return true;
        } catch (Exception e) {
            System.out.println("DB connect ERROR!");
            e.printStackTrace();
            return false;
        }
    }

    private static void preparedAllStatements() throws SQLException {
        psUserDesktop = connection.prepareStatement("SELECT desktop FROM Users WHERE login = ? AND password = ?");
    }

    public static String getUserDesktop(String login, String password) {
        String userDesktop = null;

        try {
            psUserDesktop.setString(1, login);
            psUserDesktop.setString(2, password);

            ResultSet rs = psUserDesktop.executeQuery();
            if (rs.next()){
                userDesktop = rs.getString(1);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDesktop;
    }

    public static void disconnectDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
