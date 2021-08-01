package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Page<Product> findWithFilter(ProductListParams productListParams);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);
}
