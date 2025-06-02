package org.example.limitedTime;

import org.example.entity.LimitedTimeProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LimitedTimeProductRepository extends JpaRepository<LimitedTimeProductEntity, Long> {
    @Query("SELECT c.name FROM CategoriesEntity c")
    List<String> findAllCategoryNames();

    List<LimitedTimeProductEntity> findByLimitedCategory(String categoryName);
}
