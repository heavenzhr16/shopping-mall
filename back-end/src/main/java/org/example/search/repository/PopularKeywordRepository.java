package org.example.search.repository;

import org.example.entity.PopularKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PopularKeywordRepository extends JpaRepository<PopularKeywordEntity, Long> {

    // 키워드로 인기 검색어를 찾는 메서드
    Optional<PopularKeywordEntity> findByKeyword(String keyword);
}
