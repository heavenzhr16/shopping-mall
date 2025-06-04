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
            @RequestParam(required = false) List<Integer> ratings){
        
        List<ReviewResponse> reviewList = reviewService.getSortedReviewsByProductId(productId, sort, ratings)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("review/list"); // 앞에 productId 붙여야함 추후 수정
        mv.addObject("reviewList", reviewList);
        mv.addObject("productId",productId);
        mv.addObject("sort",sort);
        mv.addObject("ratings", ratings);

        return mv;

    }

}
