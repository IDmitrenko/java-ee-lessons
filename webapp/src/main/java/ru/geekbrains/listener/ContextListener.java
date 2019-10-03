package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger logger =LoggerFactory.getLogger(ContextListener .class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application");

        // инициализируем соединение с БД
        ServletContext sc = sce.getServletContext();
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");

        if (isNotNullOrEmpty(jdbcConnectionString) ||
                isNotNullOrEmpty(username)) {
            logger.error("Connection string and DB username must be specified");
            return;
        }

        try (Connection conn = DriverManager.getConnection(jdbcConnectionString, username, password)) {
            sc.setAttribute("dbConnection", conn);
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection conn = (Connection) sce.getServletContext().getAttribute("dbConnection");
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }

    private boolean isNotNullOrEmpty(String str) {
        return str != null && str.isEmpty();
    }
}
