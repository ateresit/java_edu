package ru.geekbrains.hw;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.hw.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();

        Product product = new Product(null, "Milk", 230);
        em.persist(product);

        em.getTransaction().commit();

        em.close();
    }
}
