package ru.geekbrains.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class FirstFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // в основном нужны для проверки прав доступа к ресурсу
        response.getWriter().println("<p>Header from filter</p>");
        // можем сделать redirect
        // filterConfig.getServletContext().getRequestDispatcher("/...");
        // 2-ой вариант
//        HttpServletResponse resp = (HttpServletResponse) response;
//        resp.sendRedirect("/...");
        chain.doFilter(request, response);
        response.getWriter().println("<p>Footer from filter</p>");
    }

    @Override
    public void destroy() {

    }
}
