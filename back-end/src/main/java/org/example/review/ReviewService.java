package org.example.review;

import lombok.RequiredArgsConstructor;
import org.example.entity.ReviewsEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewsEntity> getSortedReviewsByProductId(Long productId, String sort) {
        List<ReviewsEntity> reviews = reviewRepository.findByProduct_ProductId(productId);

        if ("latest".equals(sort)) {
            return reviews.stream()
                    .sorted(Comparator.comparing(ReviewsEntity::getReviewId).reversed())
                    .collect(Collectors.toList());

        } else if ("rating".equals(sort)) {
            return reviews.stream()
                    .sorted(Comparator.comparing(ReviewsEntity::getRating).reversed())
                    .collect(Collectors.toList());

        } else if ("recommend".equals(sort)) {
            return reviews.stream()
                    .filter(r -> r.getReviewImages() != null && !r.getReviewImages().isBlank())
                    .sorted(Comparator.comparing(ReviewsEntity::getReviewId).reversed())
                    .collect(Collectors.toList());

        } else {
            return reviews;
        }
    }


}
