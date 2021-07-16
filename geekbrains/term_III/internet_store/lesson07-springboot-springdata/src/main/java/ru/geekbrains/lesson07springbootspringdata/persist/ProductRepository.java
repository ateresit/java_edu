package ru.geekbrains.lesson07springbootspringdata.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    List<Product> findByProductnameStartsWith(String prefix);

    @Query("select p " +
            "from Product p " +
            "where (p.title like CONCAT(:prefix, '%') or :prefix is null) and " +
            "(p.cost >= :minCost or :minCost is null) and " +
            "(p.cost <= :maxCost or :maxCost is null)")
    List<Product> filterProducts(@Param("prefix") String prefix,
                                 @Param("minCost") BigDecimal minCost,
                                 @Param("maxCost") BigDecimal maxCost);
}
