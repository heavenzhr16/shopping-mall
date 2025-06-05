package org.example.review;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.ReviewsEntity;

import java.util.List;

@Getter
@Setter
public class ReviewResponse {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;
    private List<String> reviewImages;
    private Long productId;
    private Long userId;

    //  private List<ReviewReplyDto> reviewReplies;

    public static ReviewResponse from(ReviewsEntity entity) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(entity.getReviewId());
        response.setReviewTitle(entity.getReviewTitle());
        response.setReviewContent(entity.getReviewContent());
        response.setRating(entity.getRating());
        response.setUserId(entity.getUser().getUserId());
        response.setProductId(entity.getProduct().getProductId());

        // ✅ 리스트로 세팅 (이미 엔티티에 리스트 필드 있음)
        response.setReviewImages(entity.getReviewImageList());

        // 추후 리뷰 댓글 추가 예정

        return response;
    }
}
