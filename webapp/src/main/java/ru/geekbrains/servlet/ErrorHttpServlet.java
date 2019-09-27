package ru.geekbrains.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHttpServlet extends HttpServlet {

    private static final Logger logger =LoggerFactory.getLogger(ErrorHttpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String title = "Error Handling";
        String docType = "<!DOCTYPE html>";
        PrintWriter writer = resp.getWriter();
        writer.println(docType + "<html><head><title>title</title></head><body>");
        writer.println("<h1>Error information</h1>");
        writer.println("Code " + code);
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
