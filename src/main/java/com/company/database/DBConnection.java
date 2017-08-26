package com.company.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для соединения с БД, спрашивает имя БД, имя и пароль пользователя. Подключается по умолчанию к localhost, порт 3306
 */
public class DBConnection {
    private String url;
    private String userName;
    private String password;


    public Connection getConnection() {
        while (true) {
            try {
                setData();
                return DriverManager.getConnection(url, userName, password);
            } catch (SQLException e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
        }
    }

    private void setData() {
        url = "jdbc:mysql://localhost:3306/" + getDBName() + "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
        userName = getUser();
        password = getPassword();
    }

    private static String getDBName() {
        ConsoleHelper.writeMessage("Enter the name of the database");
        return ConsoleHelper.readString();
    }

    private static String getUser() {
        ConsoleHelper.writeMessage("Enter user");
        return ConsoleHelper.readString();
    }

    private static String getPassword() {
        ConsoleHelper.writeMessage("Enter password");
        return ConsoleHelper.readString();
    }
}