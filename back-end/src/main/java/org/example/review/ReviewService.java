package org.example.review;

import lombok.RequiredArgsConstructor;
import org.example.entity.ProductsEntity;
import org.example.entity.ReviewsEntity;
import org.example.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // productId로 리뷰 조회
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

    // 리뷰 생성
    public ReviewResponse createReview(ReviewRequest req){
        ReviewsEntity review = new ReviewsEntity();
        review.setReviewTitle(req.getReviewTitle());
        review.setReviewContent(req.getReviewContent());
        review.setRating(req.getRating());

        // null 처리
        if(req.getReviewImages() != null){
            review.setReviewImageList(req.getReviewImages());
        }else {
            review.setReviewImageList(new ArrayList<>());
        }

        ProductsEntity product = new ProductsEntity();
        product.setProductId(req.getProductId());
        review.setProduct(product);

        UsersEntity user = new UsersEntity();
        user.setUserId(req.getUserId());
        review.setUser(user);

        ReviewsEntity saved = reviewRepository.save(review);

        return ReviewResponse.from(saved);
    }

    // 리뷰 수정
    public ReviewResponse updateReview(Long reviewId, ReviewRequest req){
        ReviewsEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰가 존재하지 않습니다 : " + reviewId));

        review.setReviewTitle(req.getReviewTitle());
        review.setReviewContent(req.getReviewContent());
        review.setRating(req.getRating());

        // 이미지 (nullable 처리 포함)
        if (req.getReviewImages() != null) {
            review.setReviewImageList(req.getReviewImages());
        } else {
            review.setReviewImageList(new ArrayList<>());
        }

        ReviewsEntity updated = reviewRepository.save(review);
        return ReviewResponse.from(updated);
    }

    public void deleteReview(Long reviewId) {
        ReviewsEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰가 존재하지 않습니다 : " + reviewId));

        reviewRepository.delete(review);
    }

    public Page<ReviewResponse> getPagedReviewsByProductId(Long productId, String sort, List<Integer> ratings, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        List<ReviewsEntity> all = reviewRepository.findByProduct_ProductId(productId);

        // 별점 필터
        if (ratings != null && !ratings.isEmpty()) {
            all = all.stream()
                    .filter(r -> r.getRating() != null && ratings.contains(r.getRating()))
                    .collect(Collectors.toList());
        }

        // 검색 기능
        if (keyword != null && !keyword.isBlank()) {
            all = all.stream()
                    .filter(r -> r.getReviewTitle().contains(keyword) || r.getReviewContent().contains(keyword))
                    .collect(Collectors.toList());
        }

        // 정렬
        if ("latest".equals(sort)) {
            all.sort(Comparator.comparing(ReviewsEntity::getReviewId).reversed());
        } else if ("rating".equals(sort)) {
            all.sort(Comparator.comparing(ReviewsEntity::getRating).reversed());
        } else if ("recommend".equals(sort)) {
            all = all.stream()
                    .filter(r -> r.getReviewImages() != null && !r.getReviewImages().isBlank())
                    .sorted(Comparator.comparing(ReviewsEntity::getReviewId).reversed())
                    .collect(Collectors.toList());
        }

        // 페이징 처리 (자바 메모리 내에서)
        // QueryDSL 나 Spring Data JPA의 Page<ReviewsEntity> 사용X
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), all.size());

        List<ReviewResponse> content = all.subList(start, end).stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, all.size());
    }

    // 별점 계산
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
