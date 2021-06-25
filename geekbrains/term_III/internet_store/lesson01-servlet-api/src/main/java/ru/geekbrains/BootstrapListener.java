package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        ProductRepository productRepository = new ProductRepository();
        productRepository.insert(new Product(1L, "Product 1", 1500));
        productRepository.insert(new Product(2L, "Product 2", 3800));
        productRepository.insert(new Product(3L, "Product 3", 8000));
        productRepository.insert(new Product(4L, "Product 4", 18000));
        productRepository.insert(new Product(5L, "Продукт 5", 83400));
        productRepository.insert(new Product(6L, "Продукт 6", 40));
        productRepository.insert(new Product(7L, "Product 7", 800));
        productRepository.insert(new Product(8L, "Product 8", 80040));
        productRepository.insert(new Product(9L, "Product 9", 8300));
        productRepository.insert(new Product(10L, "Product 10", 8100));

        sc.setAttribute("productRepository", productRepository);
    }

}
