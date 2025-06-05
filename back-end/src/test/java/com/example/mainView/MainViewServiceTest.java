package com.example.mainView;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.CategoriesEntity;
import org.example.entity.ProductsEntity;
import org.example.mainView.MainViewService;
import org.example.mainView.ProductsRepository;
import org.example.mainView.dto.ProductListResponseDto;
import org.example.mainView.dto.ProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
class MainViewServiceTest {

    @InjectMocks
    private MainViewService mainViewService;

    @Mock
    private ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductList_조건일치_정상조회() {
        // given
        ProductRequestDto request = new ProductRequestDto();
        request.setType("M");
        request.setPriceMin(10000);
        request.setPriceMax(100000);
        request.setDepth2Category("스웨트/티셔츠");
        request.setDepth3Category("후드/집업");
        request.setColors(Collections.emptyList());
        request.setSortBy("new");

        CategoriesEntity category = new CategoriesEntity();
        category.setName("후드/집업");
        category.setDepth(3L);
        category.setGenderType("M");

        ProductsEntity product1 = new ProductsEntity();
        product1.setName("테스트 상품");
        product1.setPrice(50000L);
        product1.setCategory(category);
        product1.setCreatedAt(LocalDateTime.now());

        List<ProductsEntity> mockProducts = List.of(product1);
        when(productsRepository.findByCategory_GenderTypeAndCategory_NameAndCategory_Depth(
                "M", "스웨트/티셔츠", 2L
        )).thenReturn(mockProducts);

        // when
        ProductListResponseDto response = mainViewService.getProductList(request, 1);

        // then
        assertThat(response.getCurrentPage()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getName()).isEqualTo("테스트 상품");
    }

    @Test
    void getProductList_가격범위밖_상품제외() {
        // given
        ProductRequestDto request = new ProductRequestDto();
        request.setType("M");
        request.setPriceMin(60000); // 최소 가격이 상품 가격보다 높다
        request.setPriceMax(100000);
        request.setDepth2Category("스웨트/티셔츠");

        CategoriesEntity category = new CategoriesEntity();
        category.setName("후드/집업");
        category.setDepth(3L);
        category.setGenderType("M");

        ProductsEntity product = new ProductsEntity();
        product.setName("비싼 상품");
        product.setPrice(50000L); // 필터 범위에 포함되지 않음
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        when(productsRepository.findByCategory_GenderTypeAndCategory_NameAndCategory_Depth(
                "M", "스웨트/티셔츠", 2L
        )).thenReturn(List.of(product));

        // when
        ProductListResponseDto response = mainViewService.getProductList(request, 1);

        // then
        log.info("✅ 가격 필터 테스트 - 결과 상품 수: {}", response.getProducts().size());
        assertThat(response.getProducts()).isEmpty();
    }

    @Test
    void getProductList_상세카테고리불일치_제외() {
        // given
        ProductRequestDto request = new ProductRequestDto();
        request.setType("M");
        request.setDepth2Category("스웨트/티셔츠");
        request.setDepth3Category("니트"); // 불일치

        CategoriesEntity category = new CategoriesEntity();
        category.setName("후드/집업"); // 일치하지 않음
        category.setDepth(3L);
        category.setGenderType("M");

        ProductsEntity product = new ProductsEntity();
        product.setName("카테고리 불일치 상품");
        product.setPrice(70000L);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        when(productsRepository.findByCategory_GenderTypeAndCategory_NameAndCategory_Depth(
                "M", "스웨트/티셔츠", 2L
        )).thenReturn(List.of(product));

        // when
        ProductListResponseDto response = mainViewService.getProductList(request, 1);

        // then
        log.info("✅ 상세 카테고리 필터 테스트 - 결과 상품 수: {}", response.getProducts().size());
        assertThat(response.getProducts()).isEmpty();
    }

    @Test
    void getProductList_최신순정렬() {
        // given
        ProductRequestDto request = new ProductRequestDto();
        request.setType("M");
        request.setDepth2Category("스웨트/티셔츠");
        request.setSortBy("new");

        CategoriesEntity category = new CategoriesEntity();
        category.setName("후드/집업");
        category.setDepth(3L);
        category.setGenderType("M");

        ProductsEntity oldProduct = new ProductsEntity();
        oldProduct.setName("예전 상품");
        oldProduct.setPrice(30000L);
        oldProduct.setCategory(category);
        oldProduct.setCreatedAt(LocalDateTime.now().minusDays(10));

        ProductsEntity newProduct = new ProductsEntity();
        newProduct.setName("신상품");
        newProduct.setPrice(50000L);
        newProduct.setCategory(category);
        newProduct.setCreatedAt(LocalDateTime.now());

        when(productsRepository.findByCategory_GenderTypeAndCategory_NameAndCategory_Depth(
                "M", "스웨트/티셔츠", 2L
        )).thenReturn(List.of(oldProduct, newProduct));

        // when
        ProductListResponseDto response = mainViewService.getProductList(request, 1);

        // then
        log.info("✅ 정렬 기준 테스트 - 정렬된 첫 번째 상품: {}", response.getProducts().get(0).getName());
        assertThat(response.getProducts()).hasSize(2);
        assertThat(response.getProducts().get(0).getName()).isEqualTo("신상품");
    }

}