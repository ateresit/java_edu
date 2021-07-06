package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main05 {

    public static void main(String[] args) {

        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // INSERT
/*        em.getTransaction().begin();

        User user = new User(null, "user1", 25);
        em.persist(user);

        em.getTransaction().commit();

 */
        // SELECT
        User user = em.find(User.class, 1L);
        System.out.println(user);
        em.close();
    }
}
