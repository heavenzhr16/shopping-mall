package org.example.review;

import org.example.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends
        JpaRepository<ReviewsEntity,Long> {

    // 상품 ID로 리뷰 리스트 조회
    List<ReviewsEntity> findByProduct_ProductId(Long productId);


}
