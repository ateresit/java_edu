package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> namePattern,
                             Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);
}
