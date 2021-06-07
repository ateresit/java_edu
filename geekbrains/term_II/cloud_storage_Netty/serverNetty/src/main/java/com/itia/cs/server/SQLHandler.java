package com.itia.cs.server;

import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static PreparedStatement psGetUserName;

    public static boolean connectDB(){
        System.out.println("хэндлер авторизации");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:cloud_storage.db");
            preparedAllStatements();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void preparedAllStatements() throws SQLException {
        psGetUserName = connection.prepareStatement("SELECT name FROM Users WHERE name = ? AND password = ?");
    }

    public static String getAuthyUserName(String login, String password){
        String authyUserName = null;

        try {
            psGetUserName.setString(1, login);
            psGetUserName.setString(2, password);

            ResultSet rs = psGetUserName.executeQuery();
            if (rs.next()){
                authyUserName = rs.getString(1);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authyUserName;
    }

    public static void disconnectDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
