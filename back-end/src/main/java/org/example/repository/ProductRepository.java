package org.example.repository;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {
}
