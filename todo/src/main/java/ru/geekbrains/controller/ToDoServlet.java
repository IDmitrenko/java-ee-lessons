package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ContentsOrder;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@WebServlet(name = "ToDoServlet", urlPatterns = "/todos/*")
public class ToDoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);

    private ToDoRepository repository;
    private List<ToDo> cartList = new LinkedList<>();

    @Override
    public void init() throws ServletException {
        this.repository = (ToDoRepository) getServletContext().getAttribute("toDoRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("PathInfo: {}", req.getPathInfo());

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            try {
                req.setAttribute("todos", repository.findAll());
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            req.getRequestDispatcher("/WEB-INF/templates/index.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/new")) {
            req.setAttribute("todo", new ToDo());
            req.setAttribute("action", "create");
            req.getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/edit")) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ToDo toDo;
            try {
                toDo = repository.findById(id);
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            req.setAttribute("todo", toDo);
            req.setAttribute("action", "update");
            req.getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/delete")) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return;
            }
            try {
                repository.delete(id);
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (req.getPathInfo().equals("/cart")) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ToDo toDo;
            try {
                toDo = repository.findById(id);
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            cartList.add(toDo);
            req.setAttribute("total", cartList.size());
            try {
                req.setAttribute("todos", repository.findAll());
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            req.getRequestDispatcher("/WEB-INF/templates/index.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/order")) {
            Integer value = null;
            Map<ToDo, Integer> orderMap = new LinkedHashMap<>();
            for (ToDo toDo : cartList) {
                value = orderMap.get(toDo);
                if (value == null) {
                    orderMap.put(toDo, 1);
                } else {
                    orderMap.put(toDo, value + 1);
                }
            }
            req.setAttribute("order", orderMap);
            req.getRequestDispatcher("/WEB-INF/templates/order.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/registration")) {
            req.getRequestDispatcher("/WEB-INF/templates/registration.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/title")) {
            req.getRequestDispatcher("/WEB-INF/templates/title.jsp").forward(req, resp);
        } if (req.getPathInfo().equals("/product")) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ToDo toDo;
            try {
                toDo = repository.findById(id);
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            req.setAttribute("todo", toDo);
            req.getRequestDispatcher("/WEB-INF/templates/product.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Date {}", req.getParameter("targetDate"));

        if (req.getPathInfo() != null && req.getPathInfo().equals("/update")) {
            try {
                repository.update(new ToDo(
                        Long.parseLong(req.getParameter("id")),
                        req.getParameter("description"),
                        LocalDate.parse(req.getParameter("targetDate"))));
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException | DateTimeParseException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (req.getPathInfo() != null && req.getPathInfo().equals("/create")) {
            try {
                repository.insert(new ToDo(
                        -1L,
                        req.getParameter("description"),
                        LocalDate.parse(req.getParameter("targetDate"))));
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException | DateTimeParseException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (req.getPathInfo() != null && req.getPathInfo().equals("/registration")) {
            try {
                repository.insertOrder(new Order(
                        -1L,
                        req.getParameter("name"),
                        LocalDate.now(),
                        repository.findLastNumber() + 1,
                        req.getParameter("address"),
                        req.getParameter("phone")));
                for (ToDo toDo : cartList) {
                    repository.insertContentsOrder(new ContentsOrder(
                            repository.findLastOrderId(),
                            toDo.getId()));
                }
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException | DateTimeParseException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
