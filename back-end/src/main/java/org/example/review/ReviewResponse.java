package org.example.review;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.ReviewsEntity;

@Getter
@Setter
public class ReviewResponse {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;
    private String reviewImages;
    private Long productId;
    private String productName;
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

        if (entity.getReviewImages() != null) {
            response.setReviewImages(entity.getReviewImages());
        }

        // 추후 리뷰 댓글 추가 예정

        return response;
    }
}
