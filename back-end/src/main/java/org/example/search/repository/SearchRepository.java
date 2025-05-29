package org.example.search.repository;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SearchRepository extends JpaRepository<ProductsEntity, Long> {
    // name 필드에 keyword가 포함된 상품을 찾아줌
    List<ProductsEntity> findByNameContaining(String keyword);
}
