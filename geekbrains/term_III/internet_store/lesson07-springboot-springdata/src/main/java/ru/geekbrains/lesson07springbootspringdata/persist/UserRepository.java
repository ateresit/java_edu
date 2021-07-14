package ru.geekbrains.lesson07springbootspringdata.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
