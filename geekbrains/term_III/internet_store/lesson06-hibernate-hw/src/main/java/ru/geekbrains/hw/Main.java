package ru.geekbrains.hw;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.hw.entity.Customer;
import ru.geekbrains.hw.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // Добавим товар
        em.getTransaction().begin();

        Product product = new Product(null, "Milk", new BigDecimal("230.55"));
        em.persist(product);

        em.getTransaction().commit();

        // Добавим покупателя
        em.getTransaction().begin();

        Customer customer = new Customer(null, "Fedot");
        em.persist(customer);

        em.getTransaction().commit();

        em.close();
    }
}
