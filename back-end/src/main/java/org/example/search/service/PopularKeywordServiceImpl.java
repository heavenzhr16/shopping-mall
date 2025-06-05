package org.example.search.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.PopularKeywordEntity;
import org.example.search.repository.PopularKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularKeywordServiceImpl implements PopularKeywordService {

    private final PopularKeywordRepository popularKeywordRepository;

    @Override
    public PopularKeywordEntity save(String keyword) {
        return popularKeywordRepository.findByKeyword(keyword)
                .map(entity -> {
                    entity.setSearchCount(entity.getSearchCount() + 1);
                    return popularKeywordRepository.save(entity);
                })
                .orElseGet(() -> {
                    PopularKeywordEntity newKeyword = PopularKeywordEntity.builder()
                            .keyword(keyword)
                            .searchCount(1L)
                            .build();
                    return popularKeywordRepository.save(newKeyword);
                });
    }

    @Override
    public PopularKeywordEntity findOrCreate(String keyword) {
        return popularKeywordRepository.findByKeyword(keyword)
                .orElseGet(() -> popularKeywordRepository.save(PopularKeywordEntity.builder()
                        .keyword(keyword)
                        .searchCount(1L)
                        .build()));
    }

    @Override
    public List<PopularKeywordEntity> findAll() {
        return popularKeywordRepository.findAll();
    }
}
