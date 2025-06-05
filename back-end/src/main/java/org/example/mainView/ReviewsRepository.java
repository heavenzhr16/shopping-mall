package org.example.mainView;

import org.example.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ReviewsRepository extends JpaRepository<ReviewsEntity, Long> {

    @Query("SELECT r.product.productId, COUNT(r) " +
            "FROM ReviewsEntity r " +
            "WHERE r.product.productId IN :productIds " +
            "GROUP BY r.product.productId")
    Map<Long, Long> countReviewsByProductIds(@Param("productIds") List<Long> productIds);
}
