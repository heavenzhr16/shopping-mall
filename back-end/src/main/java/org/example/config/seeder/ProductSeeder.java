package org.example.config.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.CategoriesEntity;
import org.example.entity.ProductsEntity;
import org.example.mainView.CategoriesRepository;
import org.example.mainView.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.example.entity.ColorEntity;
import org.example.mainView.dto.ColorRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductSeeder {

    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ColorRepository colorRepository;


    @Bean
    public CommandLineRunner seedProducts() {
        return args -> {
            if (productsRepository.count() > 0) {
                log.info("상품 데이터가 이미 존재합니다. 시더를 건너뜁니다.");
                return;
            }

            String[] genders = {"W", "M", "K"};
            Random random = new Random();

            for (String gender : genders) {
                // depth=1, 성별별 최상위 카테고리
                CategoriesEntity rootCategory = categoriesRepository.findByGenderTypeAndDepth(gender, 1L)
                        .orElseThrow(() -> new RuntimeException(genderToName(gender) + " 최상위 카테고리를 찾을 수 없습니다."));

                // depth=2 카테고리 리스트 조회 (rootCategory의 자식들)
                List<CategoriesEntity> secondLevelCategories = categoriesRepository.findByParentIdAndDepth(rootCategory.getCategoryId(), 2L);

                for (CategoriesEntity secondLevel : secondLevelCategories) {
                    // depth=3 카테고리 리스트 조회 (secondLevel의 자식들)
                    List<CategoriesEntity> leafCategories = categoriesRepository.findByParentIdAndDepth(secondLevel.getCategoryId(), 3L);

                    for (CategoriesEntity leaf : leafCategories) {
                        for (int i = 1; i <= 20; i++) {
                            ProductsEntity product = new ProductsEntity();
                            product.setName(genderToName(gender) + " " + secondLevel.getName() + " > " + leaf.getName() + " 상품 " + i);
                            product.setDescription("편안하고 스타일리시한 " + genderToName(gender) + "용 " + leaf.getName() + " 제품입니다.");
                            product.setPrice(50000L + random.nextInt(150000));
                            product.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
                            product.setUpdatedAt(LocalDateTime.now());
                            product.setIsActive(true);

                            // 색상 랜덤 연결
                            List<ColorEntity> allColors = colorRepository.findAll();
                            int colorCount = 1 + random.nextInt(3); // 1~3개의 색상 연결

                            Collections.shuffle(allColors);
                            List<ColorEntity> selectedColors = allColors.subList(0, colorCount);
                            product.setColors(selectedColors);

                            product.setCategory(leaf);
                            product.setDiscountRate(random.nextDouble() * 0.3);
                            product.setSalesCount((long) random.nextInt(200));
                            product.setLikes((long) random.nextInt(500));
                            product.setViews((long) random.nextInt(1000));

                            productsRepository.save(product);
                        }
                        log.info("{} {} > {} 카테고리에 20개 상품 생성 완료", genderToName(gender), secondLevel.getName(), leaf.getName());
                    }
                }
            }
        };
    }
    private String genderToName(String gender) {
        if ("W".equals(gender)) {
            return "우먼";
        } else if ("M".equals(gender)) {
            return "맨";
        } else if ("K".equals(gender)) {
            return "키즈";
        } else if ("U".equals(gender)) {
            return "남녀공용";
        } else {
            return "알 수 없는 성별";
        }
    }
}
