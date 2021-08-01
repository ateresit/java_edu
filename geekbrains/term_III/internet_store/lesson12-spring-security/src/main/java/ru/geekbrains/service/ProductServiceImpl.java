package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findWithFilter(ProductListParams productListParams) {
        Specification<Product> spec = Specification.where(null);

        if (productListParams.getProductFilter() != null && !productListParams.getProductFilter().isBlank()) {
            spec = spec.and(ProductSpecifications.productPrefix(productListParams.getProductFilter()));
        }
        if (productListParams.getMaxCost() != null) {
            spec = spec.and(ProductSpecifications.maxCost(productListParams.getMaxCost()));
        }
        if (productListParams.getMinCost() != null) {
            spec = spec.and(ProductSpecifications.minCost(productListParams.getMinCost()));
        }

        return productRepository.findAll(spec,
                PageRequest.of(Optional.ofNullable(productListParams.getPage()).orElse(1) - 1,
                        Optional.ofNullable(productListParams.getSize()).orElse(3),
                        Sort.by(Optional.ofNullable(productListParams.getSortField()).orElse("id"))));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
