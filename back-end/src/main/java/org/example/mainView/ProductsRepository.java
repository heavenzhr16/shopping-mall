package org.example.mainView;

import org.example.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    List<ProductsEntity> findTop8ByCategory_GenderTypeInOrderBySalesCountDesc(List<String> genders);
    List<ProductsEntity> findTop8ByOrderByCreatedAtDesc();

}
