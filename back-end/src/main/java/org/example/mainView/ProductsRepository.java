package org.example.mainView;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    List<ProductsEntity> findTop8ByCategory_GenderTypeInOrderBySalesCountDesc(List<String> genders);
    List<ProductsEntity> findTop8ByOrderByCreatedAtDesc();
    List<ProductsEntity> findByCategory_CategoryId(Long categoryId);
}
