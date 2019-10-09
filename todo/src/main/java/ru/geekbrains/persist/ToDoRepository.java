package ru.geekbrains.persist;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ToDoRepository {

    private final Connection conn;

    public ToDoRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    public void insert(ToDo toDo) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into todos(description, targetDate) values (?, ?);")) {
            stmt.setString(1, toDo.getDescription());
            stmt.setDate(2, Date.valueOf(toDo.getTargetDate()), Calendar.getInstance());
            stmt.execute();
        }
    }

    public void insertOrder(Order order) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into orders(name, date, numbers, address, phone) values (?, ?, ?, ?, ?);")) {
            stmt.setString(1, order.getName());
            stmt.setDate(2, Date.valueOf(order.getDate()), Calendar.getInstance());
            stmt.setInt(3, order.getNumbers());
            stmt.setString(4, order.getAddress());
            stmt.setString(5, order.getPhone());
            stmt.execute();
        }
    }

    public void insertContentsOrder(ContentsOrder contentsOrder) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into contents_order(id_order, id_todo) values (?, ?);")) {
            stmt.setLong(1, contentsOrder.getIdOrder());
            stmt.setLong(2, contentsOrder.getIdTodo());
            stmt.execute();
        }
    }

    public void update(ToDo toDo) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update todos set description = ?, targetDate = ? where id = ?;")) {
            stmt.setString(1, toDo.getDescription());
            stmt.setDate(2, Date.valueOf(toDo.getTargetDate()), Calendar.getInstance());
            stmt.setLong(3, toDo.getId());
            stmt.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from todos where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public ToDo findById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, description, targetDate from todos where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ToDo(rs.getLong(1), rs.getString(2), rs.getDate(3, Calendar.getInstance()).toLocalDate());
            }
        }
        return new ToDo(-1L, "", null);
    }

    public int findLastNumber() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select numbers from orders order by numbers desc limit 1")) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Long findLastOrderId() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id from orders order by id desc limit 1")) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0L;
    }

    public List<ToDo> findAll() throws SQLException {
        List<ToDo> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, description, targetDate from todos");

            while (rs.next()) {
                res.add(new ToDo(rs.getLong(1), rs.getString(2), rs.getDate(3, Calendar.getInstance()).toLocalDate()));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists todos (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    description varchar(25),\n" +
                    "    targetDate date \n" +
                    ");");
            stmt.execute("create table if not exists orders (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(96),\n" +
                    "    date date,\n" +
                    "    numbers int,\n" +
                    "    address varchar(256),\n" +
                    "    phone varchar(32) \n" +
                    ");");
            stmt.execute("create table if not exists contents_order (\n" +
                    "\tid_order int,\n" +
                    "    id_todo int \n" +
                    ");");
        }
    }
}
