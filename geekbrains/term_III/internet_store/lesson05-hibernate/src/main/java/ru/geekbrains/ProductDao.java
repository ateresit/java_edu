package ru.geekbrains;

import ru.geekbrains.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    private final EntityManagerFactory emFactory;

    public ProductDao(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }


    public List<Product> findAll() {
        EntityManager em = emFactory.createEntityManager();

        try {
            return em.createQuery("select p from Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void insert(Long id, String title, Integer price) {
        EntityManager em = emFactory.createEntityManager();

        try {
            em.getTransaction().begin();

            Product aProduct = new Product(id, title, price);
            em.persist(aProduct);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    public Optional<Product> findById(Long id) {
        EntityManager em = emFactory.createEntityManager();

        try {
            return Optional.ofNullable(em.find(Product.class, id));
        } finally {
            em.close();
        }
    }

    public void deleteById(Long id) {
        EntityManager em = emFactory.createEntityManager();

        try {
            em.getTransaction().begin();

            Product aProduct = em.getReference(Product.class, id);
            em.remove(aProduct);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void saveOrUpdate(Product product) {

    }

}
