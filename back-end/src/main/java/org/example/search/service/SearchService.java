package org.example.search.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.ProductsEntity;
import org.example.search.dto.SearchResponseDto;
import org.example.search.repository.SearchRepository;
import org.example.entity.PopularKeywordEntity;
import org.example.search.repository.PopularKeywordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final PopularKeywordRepository popularKeywordRepository;
    //검색어 정규화
    private String normalizeKeyword(String keyword) {
        keyword = keyword.toLowerCase(); // 소문자로 변환
        keyword = keyword.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");  //특수문자 제거
        keyword = keyword.trim().replaceAll("\\s+", " "); // 공백 정리
        return keyword;
    }


    //검색 로직
    public List<SearchResponseDto> searchByKeyword(String keyword) {
        String normalized = normalizeKeyword(keyword); // 정규화
        //대소문자 구분 없이 키워드가 name에 포함된 상품 리스트 검색
        List<ProductsEntity> products = searchRepository.searchByKeyword(normalized);

        // 검색된 ProductsEntity 리스트를 SearchResponseDto 리스트로 변환
        return products.stream()
                .map(product -> new SearchResponseDto(
                        product.getName(), //상품 이름
                        product.getPrice().intValue() //가격 (Long -> int)
                ))
                .collect(Collectors.toList());
    }

    // ✅ 인기 검색어 저장
    public void savePopularKeyword(String keyword) {
        String normalized = normalizeKeyword(keyword);

        popularKeywordRepository.findByKeyword(normalized)
                .map(entity -> {
                    entity.setSearchCount(entity.getSearchCount() + 1);
                    entity.setUpdatedAt(LocalDateTime.now());
                    return popularKeywordRepository.save(entity);
                })
                .orElseGet(() -> {
                    PopularKeywordEntity newKeyword = PopularKeywordEntity.builder()
                            .keyword(normalized)
                            .searchCount(1L)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return popularKeywordRepository.save(newKeyword);
                });
    }

    // ✅ 인기 검색어 상위 10개 반환
    public List<String> getPopularKeywords() {
        return popularKeywordRepository.findAll().stream()
                .sorted(Comparator.comparingLong(PopularKeywordEntity::getSearchCount).reversed())
                .limit(10)
                .map(PopularKeywordEntity::getKeyword)
                .collect(Collectors.toList());
    }

    //필터 조건이 적용된 검색
    public List<SearchResponseDto> searchWithFilters(String keyword, Long minPrice, Long maxPrice, Long categoryId, List<Long> colorIds){
        String normalized = normalizeKeyword(keyword); //검색어 정규화
        List<ProductsEntity> products = searchRepository.searchWithFilters(normalized, minPrice, maxPrice, categoryId, colorIds);

        return products.stream()
                .map(product -> new SearchResponseDto(
                        product.getName(),
                        product.getPrice().intValue()
                ))
                .collect(Collectors.toList());
    }

}
