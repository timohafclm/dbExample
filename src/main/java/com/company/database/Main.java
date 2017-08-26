package com.company.database;

import java.sql.Connection;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = new DBConnection().getConnection();
             DBReadWrite dbReadWrite = new DBReadWrite(connection)) {
            for (Map.Entry<String, Integer> entry : ReadFile.getData("src/main/resources/items.txt").entrySet()) {
                dbReadWrite.insertIntoItems(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Integer> entry : ReadFile.getData("src/main/resources/towns.txt").entrySet()) {
                dbReadWrite.insertIntoTowns(entry.getKey(), entry.getValue());
            }
            dbReadWrite.insertIntoShippings(1, 1, "22/08/2017", "25/08/2017");
            dbReadWrite.insertIntoShippings(1, 3, "20/08/2017", "26/08/2017");

            ConsoleApp app = new ConsoleApp(dbReadWrite);
            app.start();
            app.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
