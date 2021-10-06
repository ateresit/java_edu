package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.controller.dto.CategoryDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.ProductRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> namePattern,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecification.byName(namePattern.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getName()),
                        product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getName()),
                                product.getPictures().stream()
                                        .map(Picture::getId)
                                        .collect(Collectors.toList())));
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        if (productDto.getNewPictures() != null) {
            for (MultipartFile newPicture : productDto.getNewPictures()) {
                try {
                    product.getPictures().add(new Picture(null,
                            newPicture.getOriginalFilename(),
                            newPicture.getContentType(),
                            pictureService.createPicture(newPicture.getBytes()),
                            product
                    ));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
