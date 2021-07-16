package ru.geekbrains.lesson07springbootspringdata.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.lesson07springbootspringdata.persist.Product;
import ru.geekbrains.lesson07springbootspringdata.persist.ProductRepository;
import ru.geekbrains.lesson07springbootspringdata.persist.ProductSpecifications;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String productList(Model model,
                              @RequestParam("productFilter") Optional<String> productFilter,
                              @RequestParam("minCost") Optional<BigDecimal> minCost,
                              @RequestParam("maxCost") Optional<BigDecimal> maxCost,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam("sortField") Optional<String> sortField){
        logger.info("Product list page requested");

/*        List<Product> products = productFilter
                .map(productRepository::findByProductnameStartsWith
                .orElseGet(productRepository::findAll));*/

/*        List<Product> products = productRepository.filterProducts(
                productFilter.orElse(null),
                minCost.orElse(null),
                maxCost.orElse(null));*/

        Specification<Product> spec = Specification.where(null);
        if (productFilter.isPresent() && !productFilter.get().isBlank()) {
            spec = spec.and(ProductSpecifications.productPrefix(productFilter.get()));
        }
        if (minCost.isPresent()) {
            spec = spec.and(ProductSpecifications.minCost(minCost.get()));
        }
        if (maxCost.isPresent()) {
            spec = spec.and(ProductSpecifications.maxCost(maxCost.get()));
        }

//        model.addAttribute("products", products);
        model.addAttribute("products", productRepository.findAll(spec,
                PageRequest.of(page.orElse(1) - 1, size.orElse(3),
                        Sort.by(sortField.orElse("id")))));
        return "products";
    }

    @GetMapping("/new")
    public String addNewProduct(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new Product());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Edit product page requested");

        model.addAttribute("product", productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        return "product_form";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Update product");

        if (result.hasErrors()) {
            return "product_form";
        }

        productRepository.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        logger.info("Deleting product with id {}", id);

        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler (NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
