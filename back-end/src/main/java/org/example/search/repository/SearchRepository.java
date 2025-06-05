package org.example.search.repository;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<ProductsEntity, Long> {
    // 대소문자 구분 없이 name에 Keyword가 포함된 상품 검색

    @Query("SELECT p FROM ProductsEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductsEntity> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM ProductsEntity p " +
            "JOIN p.colors c " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:categoryId IS NULL OR p.category.categoryId = :categoryId) " +
            "AND (:hasColors = false OR c.colorId IN :colorIds)")
    List<ProductsEntity> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("categoryId") Long categoryId,
            @Param("colorIds") List<Long> colorIds
    );


}
