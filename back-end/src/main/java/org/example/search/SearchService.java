package org.example.search;

import lombok.RequiredArgsConstructor;
import org.example.entity.ProductsEntity;
import org.example.search.dto.SearchResponseDto;
import org.example.search.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;

    public List<SearchResponseDto> searchByKeyword(String keyword) {
        //DB에서 검색어에 해당하는 상품 리스트 찾기
        List<ProductsEntity> products = searchRepository.findByNameContaining(keyword);

        // ProductsEntity 리스트를 SearchResponseDto 리스트로 변환
        return products.stream()
                .map(product -> new SearchResponseDto(
                        product.getName(), //상품 이름
                        product.getPrice().intValue() //가격 (Long -> int)
                ))
                .collect(Collectors.toList());

    }
}
