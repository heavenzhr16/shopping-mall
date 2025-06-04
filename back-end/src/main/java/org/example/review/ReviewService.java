package org.example.review;

import lombok.RequiredArgsConstructor;
import org.example.entity.ReviewsEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewsEntity> getSortedReviewsByProductId(Long productId, String sort, List<Integer> ratings, String keyword) {
        List<ReviewsEntity> reviews = reviewRepository.findByProduct_ProductId(productId);

        // 별점 필터
        if (ratings != null && !ratings.isEmpty()){
            reviews = reviews.stream()
                    .filter(r -> r.getRating() != null && ratings.contains(r.getRating()))
                    .collect(Collectors.toList());
        }

        // 키워드 검색
        if (keyword != null && !keyword.isBlank()){
            String lowerKeyword = keyword.toLowerCase();
            reviews = reviews.stream()
                    .filter(r ->
                            (r.getReviewTitle() != null && r.getReviewTitle().toLowerCase().contains(lowerKeyword)) ||
                                    (r.getReviewContent() != null && r.getReviewContent().toLowerCase().contains(lowerKeyword))
                    )
                    .collect(Collectors.toList());
        }

        // 정렬
        if ("latest".equals(sort)) {
            reviews = reviews.stream()
                    .sorted(Comparator.comparing(ReviewsEntity::getReviewId).reversed())
                    .collect(Collectors.toList());

        } else if ("rating".equals(sort)) {
            reviews = reviews.stream()
                    .sorted(Comparator.comparing(ReviewsEntity::getRating).reversed())
                    .collect(Collectors.toList());

        } else if ("recommend".equals(sort)) {
            reviews = reviews.stream()
                    .filter(r -> r.getReviewImages() != null && !r.getReviewImages().isBlank())
                    .sorted(Comparator.comparing(ReviewsEntity::getReviewId).reversed())
                    .collect(Collectors.toList());

        }

        return reviews;

    }

    public Map<String, Object> getRatingSummaryByProductId(Long productId){
        List<ReviewsEntity> reviews = reviewRepository.findByProduct_ProductId(productId);

        long total = reviews.size();
        double average = reviews.stream()
                .mapToInt(ReviewsEntity::getRating)
                .average()
                .orElse(0.0);

        // 별점, 별점별 개수
        Map<Integer, Long> ratingCounts = reviews.stream()
                .collect(Collectors.groupingBy(
                        ReviewsEntity::getRating,
                        Collectors.counting()
                ));

        // 없는 값은 0으로 채움
        for (int i = 1; i <= 5; i++){
            // 해당 key가 없으면 0L 을 넣고 있으면 아무것도 안 함
            ratingCounts.putIfAbsent(i, 0L);
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalReviews", total);
        summary.put("averageRating", Math.round(average * 10.0) / 10.0); // 소수점 1자리 반올림
        summary.put("ratingCounts", ratingCounts);

        return summary;
    }


}
