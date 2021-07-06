package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * имитация базы данных
 */
@Repository
public class ProductRepository {
    private Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init(){
        this.insert(new Product("Продукт №1", 12000));
        this.insert(new Product("Продукт №2", 345));
        this.insert(new Product("Продукт №3", 5600));
        this.insert(new Product("Продукт №4", 3300));
        this.insert(new Product("Продукт №5", 6500));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public void insert(Product product) {
        Long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productMap.get(id));
    }

    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    public void delete(Long id) {
        productMap.remove(id);
    }
/*
// формируется ошибка - НЕ НРАВИТСЯ для условия, что LONG == null
    public void save(Product product) {
        if (product.getId() == null) {
            long id = idetity.incrementAndGet();
            product.setId(id);
        }
        productMap.put(product.getId(), product);
    }

 */

}
