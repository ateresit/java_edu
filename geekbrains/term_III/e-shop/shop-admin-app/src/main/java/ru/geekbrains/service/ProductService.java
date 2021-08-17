package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.dto.ProductDto;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);

    void save(ProductDto productDto);

    void deleteById(Long id);
}
