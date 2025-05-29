package org.example.mainView.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductRequestDto {

    //성별 필터
    @NotBlank(message = "성별 타입은 필수입니다. 예: M, W, K, U")
    private String type; // 예: "M", "W", "K", "U"

    //가격필터
    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    private int priceMin;

    @Min(value = 0, message = "최대 가격은 0 이상이어야 합니다.")
    private int priceMax;

    //중간 카테고리 필터
    @NotBlank
    private String depth2Category; // 예: "티셔츠/셔츠", "아우터" 등

    //상세 카테고리 필터
    private String depth3Category; // 예: "반소매 티셔츠", "후드/집업" 등

    //색상 필터
    private List<String> colors; // 예: ["#ff0000", "#00ff00", "#0000ff"]

    //정렬 기준
    private String sortBy = "sales";
    /*
        * 정렬 기준 예시:
        * - sales: 판매량 기준 (기본값)
        * - name: 상품명 오름차순
        * - price: 낮은 가격순
        * - priceDesc: 높은 가격순
        * - new: 최신 상품
        * - views: 조회수 기준
        * - likes: 찜 수 기준
        * - reviews: 리뷰 수 기준
    **/
}
