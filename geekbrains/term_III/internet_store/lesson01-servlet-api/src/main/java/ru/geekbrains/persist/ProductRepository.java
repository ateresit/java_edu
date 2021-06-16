package ru.geekbrains.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * имитация базы данных
 */
public class ProductRepository {
    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong idetity = new AtomicLong(0);

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public void insert(Product product) {
        long id = idetity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    public void delete(long id) {
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
