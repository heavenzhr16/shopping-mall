package org.example.limitedTime;

import org.example.entity.LimitedTimeProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LimitedTimeProductRepository extends JpaRepository<LimitedTimeProductEntity, Long> {
    @Query(value = "SELECT DISTINCT limited_category FROM limited_time_products", nativeQuery = true)
    List<String> findAllCategoryNames();

    List<LimitedTimeProductEntity> findByLimitedCategory(String categoryName);

    Optional<LimitedTimeProductEntity> findByProduct_ProductId(Long productId);
}
