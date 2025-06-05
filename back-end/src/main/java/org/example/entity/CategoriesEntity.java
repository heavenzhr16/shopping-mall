package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class CategoriesEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "depth", nullable = false)
    private Long depth;

    @Column(name = "gender_type", length = 1, nullable = false)
    private String genderType;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductsEntity> products;

    // 기본 생성자
    public CategoriesEntity() {}

}