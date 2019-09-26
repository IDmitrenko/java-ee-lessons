package ru.geekbrains.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/redirect")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Атрибуты есть у сессии
        // req.getSession().setAttribute();
        // ответ с кодом 30. и новый URL браузеру
//        resp.sendRedirect("https://ya.ru");
        // переадресация на другой сервлет
        resp.sendRedirect(req.getContextPath() + "/first/http/servlet");
    }
}
