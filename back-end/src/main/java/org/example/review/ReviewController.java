package org.example.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ModelAndView listReviewByProductId(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "recommend")String sort,
            @RequestParam(required = false) List<Integer> ratings,
            @RequestParam(required = false) String keyword){

        // 리뷰 리스트
        List<ReviewResponse> reviewList = reviewService.getSortedReviewsByProductId(productId, sort, ratings, keyword)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());

        // 별점 표시
        Map<String,Object> ratingSummary = reviewService.getRatingSummaryByProductId(productId);

        ModelAndView mv = new ModelAndView("review/list"); // 앞에 productId 붙여야함 추후 수정
        mv.addObject("reviewList", reviewList);
        mv.addObject("productId",productId);
        mv.addObject("sort",sort);
        mv.addObject("ratings", ratings);

        // 별점 요약 정보
        mv.addObject("averageRating", ratingSummary.get("averageRating"));
        mv.addObject("totalReviews", ratingSummary.get("totalReviews"));
        mv.addObject("ratingCounts", ratingSummary.get("ratingCounts"));

        // 검색어
        mv.addObject("ratings", ratings);

        return mv;

    }

}
