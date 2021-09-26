package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByUsernameStartsWith(String prefix);

    @Query("select u " +
            "from User u " +
            "where ( u.username like CONCAT(:prefix, '%') or :prefix is null )")
    List<User> filterUsers(@Param("prefix") String prefix);

    @Query("select distinct u " +
            "from User u " +
            "left join fetch u.roles " +
            "where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
