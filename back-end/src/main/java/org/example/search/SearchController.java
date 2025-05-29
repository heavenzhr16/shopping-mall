package org.example.search;

import lombok.RequiredArgsConstructor;
import org.example.search.dto.SearchRequestDto;
import org.example.search.dto.SearchResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    @PostMapping
    public List<SearchResponseDto> search(@RequestBody SearchRequestDto request) {
        // 사용자가 보낸 검색어를 꺼내서
        String keyword = request.getKeyword();

        //검색 서비스 호출 -> 결과 리스트 받아서 응답
        return searchService.searchByKeyword(keyword);
    }
}
