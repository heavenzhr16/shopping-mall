package org.example.review;

import lombok.RequiredArgsConstructor;
import org.example.entity.ReviewsEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewsEntity> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_ProductId(productId);
    }
}
