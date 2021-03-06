package com.ydt.repository;

import com.ydt.entity.Category;
import com.ydt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select o from Product o where o.id_cate.category_ID = :orderNumber")
    List<Product> getProductByCate(@Param("orderNumber") Long orderNumber);
}
