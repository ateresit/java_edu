package ru.geekbrains;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Product;

import javax.persistence.EntityManagerFactory;

public class ProductMain {

    public static void main(String[] args) {

        /*EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();*/

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;
        session = sessionFactory.getCurrentSession();

        

        sessionFactory.close();
    }
}
