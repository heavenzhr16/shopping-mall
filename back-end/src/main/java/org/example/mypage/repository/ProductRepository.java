package org.example.mypage.repository;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 Repository
 * 상품 관련 기본 CRUD 기능 제공
 */
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {
  // 필요 시 사용자 정의 메서드 추가 예정
}
