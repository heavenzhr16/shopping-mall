package org.example.config.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.CategoriesEntity;
import org.example.mainView.CategoriesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CategorySeeder {

    private final CategoriesRepository categoriesRepository;

    @Bean
    public CommandLineRunner seedCategories() {
        return args -> {
            if (categoriesRepository.count() > 0) {
                log.info("카테고리 데이터가 이미 존재합니다. 시더를 건너뜁니다.");
                return;
            }

            // 우먼
            Map<String, List<String>> womanCategories = Map.of(
                    "아우터", List.of("패딩/다운", "경량패딩", "점퍼/무스탕", "코트", "재킷/블레이저", "플리스/덤블", "바람막이"),
                    "스웨터/티셔츠", List.of("맨투맨/스웨트셔츠", "후드/집업", "긴소매 티셔츠", "반소매 티셔츠", "슬리브리스"),
                    "니트/카디건", List.of("카디건", "니트/스웨터"),
                    "셔츠/블라우스", List.of("셔츠", "블라우스"),
                    "팬츠", List.of("슬랙스", "트레이닝/스웨트팬츠", "캐주얼팬츠", "쇼츠"),
                    "원피스/스커트", List.of("원피스", "스커트"),
                    "데님", List.of("슬림", "스트레이트", "부츠컷", "와이드", "원피스/스커트", "쇼츠", "셔츠/재킷"),
                    "액세서리", List.of("가방", "모자", "머플러", "벨트", "장갑/우산", "신발", "기타"),
                    "이너웨어", List.of("웜테크", "쿨테크", "브라", "팬티", "패키지", "양말/타이즈"),
                    "홈웨어", List.of("홈웨어")
            );
            insertCategoryTree("우먼", "W", womanCategories);

            // 맨
            Map<String, List<String>> manCategories = Map.of(
                    "아우터", List.of("패딩/다운", "경량패딩", "점퍼/무스탕", "코트", "재킷/블레이저", "플리스/덤블", "바람막이"),
                    "스웨터/티셔츠", List.of("맨투맨/스웨트셔츠", "후드/집업", "긴소매 티셔츠", "반소매 티셔츠", "슬리브리스"),
                    "니트/카디건", List.of("카디건", "니트/스웨터"),
                    "셔츠", List.of("셔츠"),
                    "팬츠", List.of("슬랙스", "트레이닝/스웨트팬츠", "캐주얼팬츠", "쇼츠"),
                    "데님", List.of("슬림", "테이퍼드", "스트레이트", "와이드", "쇼츠/재킷"),
                    "액세서리", List.of("가방", "모자", "머플러", "벨트", "장갑/우산", "신발", "기타"),
                    "이너웨어", List.of("웜테크", "쿨테크", "팬티", "패키지", "양말"),
                    "홈웨어", List.of("홈웨어")
            );
            insertCategoryTree("맨", "M", manCategories);

            // 남녀공용
            Map<String, List<String>> unisexCategories = Map.of(
                    "아우터", List.of("패딩/다운", "경량패딩", "점퍼/무스탕", "코트", "재킷/블레이저", "플리스/덤블", "바람막이"),
                    "스웨터/티셔츠", List.of("맨투맨/스웨트셔츠", "후드/집업", "긴소매 티셔츠", "반소매 티셔츠", "슬리브리스"),
                    "니트/카디건", List.of("카디건", "니트/스웨터"),
                    "팬츠", List.of("슬랙스", "트레이닝/스웨트팬츠", "캐주얼팬츠", "쇼츠"),
                    "액세서리", List.of("가방", "모자", "머플러", "벨트", "장갑/우산", "신발", "기타"),
                    "홈웨어", List.of("홈웨어")
            );
            insertCategoryTree("남녀공용", "U", unisexCategories);

            // 키즈
            Map<String, List<String>> kidsCategories = Map.of(
                    "아우터", List.of("바람막이", "재킷/점퍼", "헤비아우터"),
                    "상의", List.of("긴소매 티셔츠", "반소매 티셔츠", "맨투맨/후드/집업", "니트/카디건", "셔츠/블라우스"),
                    "하의", List.of("스웨트팬츠", "캐주얼팬츠", "데님", "반바지"),
                    "원피스/스커트", List.of("원피스/스커트"),
                    "상하 세트", List.of("긴소매", "반소매"),
                    "데님", List.of("상의", "바지", "스커트/쇼츠"),
                    "액세서리", List.of("가방", "모자", "머플러", "기타잡화"),
                    "이너웨어", List.of("쿨테크", "웜테크", "언더웨어", "패키지", "양말/타이즈"),
                    "파자마", List.of("반소매 파자마", "긴소매파자마", "수면파자마")
            );
            insertCategoryTree("키즈", "K", kidsCategories);
        };
    }

    private void insertCategoryTree(String rootName, String genderType, Map<String, List<String>> subcategoriesMap) {
        CategoriesEntity rootCategory = new CategoriesEntity();
        rootCategory.setName(rootName);
        rootCategory.setGenderType(genderType);
        rootCategory.setDepth(1L);
        rootCategory.setParentId(null);
        categoriesRepository.save(rootCategory);

        for (Map.Entry<String, List<String>> middleEntry : subcategoriesMap.entrySet()) {
            CategoriesEntity middle = new CategoriesEntity();
            middle.setName(middleEntry.getKey());
            middle.setGenderType(genderType);
            middle.setDepth(2L);
            middle.setParentId(rootCategory.getCategoryId());
            categoriesRepository.save(middle);

            for (String leafName : middleEntry.getValue()) {
                CategoriesEntity leaf = new CategoriesEntity();
                leaf.setName(leafName);
                leaf.setGenderType(genderType);
                leaf.setDepth(3L);
                leaf.setParentId(middle.getCategoryId());
                categoriesRepository.save(leaf);
            }
        }
    }
}