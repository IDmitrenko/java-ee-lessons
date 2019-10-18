package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
@Named
public class ToDoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepository.class);

    @Inject
    private DataSource dataSource;

    private Connection conn;

    @PostConstruct
    public void init() throws SQLException {

        this.conn = dataSource.getConnection();

        try {
            createTableIfNotExists(conn);

            if (this.findAllCategory().isEmpty()) {
                this.insertCategory(new Category(-1, "Фрукты"));
                this.insertCategory(new Category(-1, "Овощи"));

                if (this.findAll().isEmpty()) {
                    this.insert(new ToDo(-1L, 1, "Apples", LocalDate.now()));
                    this.insert(new ToDo(-1L, 1, "Pears", LocalDate.now()));
                    this.insert(new ToDo(-1L, 1, "Oranges", LocalDate.now()));
                    this.insert(new ToDo(-1L, 1, "Pineapples", LocalDate.now()));
                    this.insert(new ToDo(-1L, 1, "Strawberry", LocalDate.now().plusDays(1)));
                    this.insert(new ToDo(-1L, 1, "Cherry", LocalDate.now().plusDays(1)));
                    this.insert(new ToDo(-1L, 1, "Lemons", LocalDate.now().plusDays(1)));
                    this.insert(new ToDo(-1L, 1, "Currant", LocalDate.now().plusDays(1)));
                    this.insert(new ToDo(-1L, 1, "Viburnum", LocalDate.now().plusDays(1)));
                }
            }
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }

    public void insert(ToDo toDo) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into todos(idCategory, description, targetDate) values (?, ?, ?);")) {
            stmt.setInt(1, toDo.getIdCategory());
            stmt.setString(2, toDo.getDescription());
            stmt.setDate(3, Date.valueOf(toDo.getTargetDate()), Calendar.getInstance());
            stmt.execute();
        }
    }

    public void insertCategory(Category category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into category(description) values (?);")) {
            stmt.setString(1, category.getDescription());
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
                "update todos set idCategory = ?, description = ?, targetDate = ? where id = ?;")) {
            stmt.setInt(1, toDo.getIdCategory());
            stmt.setString(2, toDo.getDescription());
            stmt.setDate(3, Date.valueOf(toDo.getTargetDate()), Calendar.getInstance());
            stmt.setLong(4, toDo.getId());
            stmt.execute();
        }
    }

    public void updateCategory(Category category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update category set description = ? where id = ?;")) {
            stmt.setString(1, category.getDescription());
            stmt.setLong(2, category.getId());
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

    public void deleteCategory(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from category where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public ToDo findById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, idCategory, description, targetDate from todos where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ToDo(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getDate(4, Calendar.getInstance()).toLocalDate());
            }
        }
        return new ToDo(-1L, -1, "", null);
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
            ResultSet rs = stmt.executeQuery("select t.id, t.idCategory, t.description, t.targetDate, c.description from todos t inner join category c on t.idCategory = c.id");

            while (rs.next()) {
                res.add(new ToDo(rs.getLong(1), rs.getInt(2), rs.getString(3),
                        rs.getDate(4, Calendar.getInstance()).toLocalDate()
                        , rs.getString(5)));
            }
        }
        return res;
    }

    public List<Category> findAllCategory() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, description from category");

            while (rs.next()) {
                categoryList.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        }
        return categoryList;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists todos (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    idCategory int, \n" +
                    "    description varchar(64),\n" +
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
            stmt.execute("create table if not exists category (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    description varchar(64) \n" +
                    ");");
        }
    }
}
