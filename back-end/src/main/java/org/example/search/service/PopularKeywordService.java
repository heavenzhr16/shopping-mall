package org.example.search.service;
import org.example.entity.PopularKeywordEntity;

import java.util.List;

public interface PopularKeywordService {
    PopularKeywordEntity save(String keyword);
    PopularKeywordEntity findOrCreate(String keyword);
    List<PopularKeywordEntity> findAll();
}

