package org.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entity.ReviewsEntity;

public interface ReviewRepository extends JpaRepository<ReviewsEntity, Long> {
    Page<ReviewsEntity> findByProduct_ProductId(Long productId, Pageable pageable);
}

