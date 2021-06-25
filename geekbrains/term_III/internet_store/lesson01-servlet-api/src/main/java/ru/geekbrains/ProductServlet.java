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

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

/*
        resp.setContentType("text/html; charset=UTF-8"); // перенесено в фильтр, см. класс EncodingFilter

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"), true);
 */
        PrintWriter writer = resp.getWriter();

        if (req.getPathInfo() == null) {
            List<Product> products = productRepository.findAll();

            /**
             * создание таблицы для вывода продукции
             */
            writer.println("<h1> Таблица с продуктами </h1>");
            writer.println("<table border=\"1\">");
            writer.println("<tr><td> ID </td><td> Наименование </td><td> Цена </td></tr>");

            for (int i = 0; i < products.size(); i++) {
                writer.println("<tr>");
                writer.println("<td>" + products.get(i).getId() + "</td><td>" +
                        "<a href =" + req.getContextPath() + req.getServletPath() + "/" + products.get(i).getId() + ">" +
                        products.get(i).getTitle() + "</a></td><td>" +
                        products.get(i).getCost() + "</td>");
                writer.println("</tr>");
            }

            writer.println("</table>");
        } else {
            String pathInfo = req.getPathInfo();
            pathInfo = pathInfo.replaceAll("[^A-Za-zА-Яа-я0-9]", "");

            Long id = Long.parseLong(pathInfo);
//            Long id = -1L;
            Product product = productRepository.findById(id);
            writer.println("<h1> Карточка продукта \"" + product.getTitle() + "\"</h1></br>");
            writer.println("Продукт прекрасный!</br>");
            writer.println("Цена продукта: " + product.getCost() + " руб.");
        }

    }
}
