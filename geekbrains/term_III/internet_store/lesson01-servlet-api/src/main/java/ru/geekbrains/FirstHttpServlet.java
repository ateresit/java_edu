package ru.geekbrains;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/http_servlet/*")
public class FirstHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Сервлет для получения частей строки сообщения: </h1>");
        resp.getWriter().println("<p>часть сообщения ИМЯ ПРИЛОЖЕНИЯ (ContextPath): " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>часть сообщения ИМЯ СЕРВЛЕТА (ServletPath): " + req.getServletPath() + "</p>");
        resp.getWriter().println("<p>часть сообщения PathInfo: " + req.getPathInfo() + "</p>");
        resp.getWriter().println("<p>часть сообщения QueryString: " + req.getQueryString() + "</p>");
        resp.getWriter().println("<p>часть сообщения param 1: " + req.getParameter("param1") + "</p>");
        resp.getWriter().println("<p>часть сообщения param 2: " + req.getParameter("param2") + "</p>");
    }
}
