package org.example.mainView;

import org.example.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {

    // genderType으로 depth 1인 최상위 카테고리 조회 (Optional, 하나만 있다고 가정)
    Optional<CategoriesEntity> findByGenderTypeAndDepth(String genderType, Long depth);

    // parentId와 depth로 하위 카테고리 리스트 조회
    List<CategoriesEntity> findByParentIdAndDepth(Long parentId, Long depth);
}
