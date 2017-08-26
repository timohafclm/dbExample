package com.company.database;


import java.sql.ResultSet;

/**
 * Класс обработки команд с консоли и вывода информации
 */
public class ConsoleApp extends Thread {
    private DBReadWrite dbReadWrite;
    private String[] buffer;

    public ConsoleApp(DBReadWrite dbReadWrite) {
        this.dbReadWrite = dbReadWrite;
    }

    private void viewItemsTownsResult(String tableName, ResultSet resultSet) throws Exception {
        String line;
        if (tableName.equals("items")) ConsoleHelper.writeMessage("ITEMS:");
        else if (tableName.equals("towns")) ConsoleHelper.writeMessage("TOWNS:");
        while (resultSet.next()) {
            line = resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" +
                    resultSet.getString(3);
            ConsoleHelper.writeMessage(line);
        }
    }

    private void viewShippingsResult(ResultSet resultSet) throws Exception {
        String line;
        ConsoleHelper.writeMessage("SHIPPINGS:");
        while (resultSet.next()) {
            line = resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" +
                    resultSet.getString(3) + "\t" + resultSet.getString(4) + "\t" + resultSet.getString(5);
            ConsoleHelper.writeMessage(line);
        }
    }

    @Override
    public void run() {
        while (true) {
            String message = ConsoleHelper.readString();
            buffer = message.split(" ");
            try {
                switch (buffer[0]) {
                    case "add_item":
                        dbReadWrite.insertIntoItems(buffer[1], Integer.parseInt(buffer[2]));
                        continue;
                    case "add_town":
                        dbReadWrite.insertIntoTowns(buffer[1], Integer.parseInt(buffer[2]));
                        continue;
                    case "add_shipping":
                        dbReadWrite.insertIntoShippings(Integer.parseInt(buffer[1]), Integer.parseInt(buffer[2]), buffer[3], buffer[4]);
                        continue;
                    case "delete_item":
                        dbReadWrite.deleteItem(Integer.parseInt(buffer[1]));
                        continue;
                    case "delete_town":
                        dbReadWrite.deleteTown(Integer.parseInt(buffer[1]));
                        continue;
                    case "delete_shipping":
                        dbReadWrite.deleteShipping(Integer.parseInt(buffer[1]));
                        continue;
                    case "show_items":
                        viewItemsTownsResult("items", dbReadWrite.selectAllItems());
                        continue;
                    case "show_towns":
                        viewItemsTownsResult("towns", dbReadWrite.selectAllTowns());
                        continue;
                    case "show_shippings":
                        viewShippingsResult(dbReadWrite.selectAllShippings());
                        continue;
                        default:ConsoleHelper.writeMessage("Error. Repeat input"); continue;
                }
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Error. Repeat input.");
                continue;
            }
        }
    }
}
