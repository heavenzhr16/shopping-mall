package org.example.mainView;

import org.example.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {

    // genderType으로 depth 1인 최상위 카테고리 조회 (Optional, 하나만 있다고 가정)
    Optional<CategoriesEntity> findByGenderTypeAndDepth(String genderType, Long depth);

    // parentId와 depth로 하위 카테고리 리스트 조회
    List<CategoriesEntity> findByParentIdAndDepth(Long parentId, Long depth);

    @Query("SELECT c.categoryId FROM CategoriesEntity c WHERE c.genderType = :genderType AND c.name = :name AND c.depth = :depth")
    Optional<Long> findCategoryIdByGenderTypeAndNameAndDepth(@Param("genderType") String genderType,
                                                             @Param("name") String name,
                                                             @Param("depth") Long depth);
}
