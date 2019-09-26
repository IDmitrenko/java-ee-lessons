package ru.geekbrains.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/forward")
public class ForwardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // атрибуты привязываются к запросу на стороне сервера
        req.setAttribute("forwarded", true);

        // переадресация с помощью forward - URL не меняется (завершает обработку запроса)
        req.getRequestDispatcher("/firstservlet/*").forward(req, resp);
        // include - (объединяет работу нескольких сервлетов)
        // forward от redirect отличается тем, что forward делает переадресацию без участия клиента
        // исключительно на сервере. Redirect выполняется и на стороне клиента.
    }
}
