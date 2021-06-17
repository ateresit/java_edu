package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productRepository.findAll();
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"), true);

        /**
         * создание таблицы для вывода продукции
         */
        writer.println("<h1> Таблица с продуктами </h1>");
        writer.println("<table border=\"1\">");
        writer.println("<tr><td> ID </td><td> Наименование </td><td> Цена </td></tr>");

        for (int i = 0; i < products.size(); i++) {
            writer.println("<tr>");
            writer.println("<td>" + products.get(i).getId() + "</td><td>" +
                                    products.get(i).getTitle() + "</td><td>" +
                                    products.get(i).getCost() + "</td>");
            writer.println("</tr>");
        }

        writer.println("</table>");

    }
}
