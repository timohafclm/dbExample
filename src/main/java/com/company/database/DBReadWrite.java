package com.company.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для чтения и записи данных в БД. Методы сделаны и выполняют конкретные задачи, в целом их нужно рефакторить,
 * выносить дублирующийся функционал
 */
public class DBReadWrite implements Closeable {
    private Statement statement;

    public DBReadWrite(Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoItems(String itemName, Integer quantity) throws Exception {
        if (itemName.length() < 30 && quantity >= 0)
            statement.execute(String.format("INSERT INTO items(item_name, quantity) VALUES('%s', %d);", itemName, quantity));
        else throw new SQLException();
    }

    public void insertIntoTowns(String townName, Integer distance) throws Exception {
        if (townName.length() < 30 && distance >= 0)
            statement.execute(String.format("INSERT INTO towns(town_name, distance) VALUES('%s', %d);", townName, distance));
        else throw new SQLException();
    }

    public void insertIntoShippings(Integer itemId, Integer townId, String startDate, String endDate) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date start = simpleDateFormat.parse(startDate);
        Date end = simpleDateFormat.parse(endDate);
        SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (start.getTime() <= end.getTime()) {
            if (selectFromTableWhereNumber("items", "item_id", itemId).next() &&
                    selectFromTableWhereNumber("towns", "town_id", townId).next())
                statement.execute(String.format("INSERT INTO shippings(item_id, town_id, start_date, end_date) VALUES" +
                        "('%d', '%d', '%s', '%s');", itemId, townId, dateFormatSQL.format(start), dateFormatSQL.format(end)));
            else
                ConsoleHelper.writeMessage("Отсутствует первичный ключ.");
        } else ConsoleHelper.writeMessage("Дата окончания не может быть меньше даты начала.");
    }

    public ResultSet selectAllItems() throws Exception {
        return statement.executeQuery("SELECT * FROM items;");
    }

    public ResultSet selectAllTowns() throws Exception {
        return statement.executeQuery("SELECT * FROM towns;");
    }

    public ResultSet selectAllShippings() throws Exception {
        return statement.executeQuery("SELECT * FROM shippings;");
    }

    public ResultSet selectFromTableWhereNumber(String tableName, String fieldName, Integer conditions) throws Exception {
        return statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s=%d", fieldName, tableName, fieldName, conditions));
    }

    public ResultSet selectItemIdFromShippings(Integer itemId) throws Exception {
        return statement.executeQuery(String.format("SELECT item_id FROM shippings WHERE item_id=%d;", itemId));
    }

    public ResultSet selectTownIdFromShippings(Integer townId) throws Exception {
        return statement.executeQuery(String.format("SELECT town_id FROM shippings WHERE town_id=%d;", townId));
    }

    public void deleteItem(Integer itemId) throws Exception {
        if (!selectItemIdFromShippings(itemId).next())
            statement.execute("DELETE FROM items WHERE item_id=" + itemId + ";");
        else ConsoleHelper.writeMessage("Эти данные используются в таблице SHIPPINGS. Удаление запрещено.");
    }

    public void deleteTown(Integer townId) throws Exception {
        if (!selectTownIdFromShippings(townId).next())
            statement.execute("DELETE FROM towns WHERE town_id=" + townId + ";");
        else ConsoleHelper.writeMessage("Эти данные используются в таблице SHIPPINGS. Удаление запрещено.");
    }

    public void deleteShipping(Integer shippingId) throws Exception {
        statement.execute("DELETE FROM shippings WHERE shipping_id=" + shippingId + ";");
    }

    @Override
    public void close() throws IOException {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}