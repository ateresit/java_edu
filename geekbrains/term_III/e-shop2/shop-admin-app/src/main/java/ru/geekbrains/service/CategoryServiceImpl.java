package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.CategoryDto;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDto> findAll(Integer page, Integer size, String sortField) {
        return categoryRepository.findAll(PageRequest.of(page, size, Sort.by(sortField)))
                .map(category -> new CategoryDto(category.getId(), category.getName()));
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDto(category.getId(), category.getName()));
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
