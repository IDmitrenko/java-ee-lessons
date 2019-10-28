package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
@Named
public class ToDoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @PostConstruct
    public void init() {

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
    }

    @Transactional
    public void insert(ToDo toDo) {
        // добавление информации через EntityManager
        em.persist(toDo);
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

    @Transactional
    public void update(ToDo toDo) {
        em.merge(toDo);
    }

    public void updateCategory(Category category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update category set description = ? where id = ?;")) {
            stmt.setString(1, category.getDescription());
            stmt.setLong(2, category.getId());
            stmt.execute();
        }
    }

    @Transactional
    public void delete(long id) {
        ToDo toDo = em.find(ToDo.class, id);
        if (toDo != null) {
            em.remove(toDo);
        }
    }

    public void deleteCategory(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from category where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public ToDo findById(long id) {
        return em.find(ToDo.class, id);
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

    public List<ToDo> findAll() {
        return em.createQuery("from ToDo ", ToDo.class).getResultList();
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
