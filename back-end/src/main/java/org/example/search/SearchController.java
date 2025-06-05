package org.example.search;

import lombok.RequiredArgsConstructor;
import org.example.search.dto.SearchFilterRequestDto;
import org.example.search.dto.SearchRequestDto;
import org.example.search.dto.SearchResponseDto;
import org.example.search.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    // 검색 요청 시 검색어 저장 및 검색 결과 반환
    @PostMapping
    public List<SearchResponseDto> search(@RequestBody SearchRequestDto request) {
        // 사용자가 보낸 검색어를 꺼내서
        String keyword = request.getKeyword();

        //검색어 저장
        searchService.savePopularKeyword(keyword);

        //검색 서비스 호출 -> 결과 리스트 받아서 응답
        return searchService.searchByKeyword(keyword);
    }

    // 인기 검색어 상위 10개 조회
    @GetMapping("/popular")
    public List<String> getPopularKeywords() {
        return searchService.getPopularKeywords();
    }

    //상품 필터 검색
    @PostMapping("/filter")
    public List<SearchResponseDto> searchWithFilters(@RequestBody SearchRequestDto request) {
        return searchService.searchWithFilters(
                request.getKeyword(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getCategoryId(),
                request.getColorIds()
        );
    }



}
