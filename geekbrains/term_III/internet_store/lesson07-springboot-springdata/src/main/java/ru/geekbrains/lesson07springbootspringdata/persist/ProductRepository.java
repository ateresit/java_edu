package ru.geekbrains.lesson07springbootspringdata.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
