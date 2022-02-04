package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // start insert
        em.getTransaction().begin();

        Student student = new Student(null, "Ivanov", 5);
        em.persist(student);

        em.getTransaction().commit();
        // end insert

        em.close();
    }
}
