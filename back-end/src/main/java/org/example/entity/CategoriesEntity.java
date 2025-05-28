package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoriesEntity {
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

    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getDepth() { return depth; }
    public void setDepth(Long depth) { this.depth = depth; }

    public String getGenderType() { return genderType; }
    public void setGenderType(String genderType) { this.genderType = genderType; }

    public List<ProductsEntity> getProducts() { return products; }
    public void setProducts(List<ProductsEntity> products) { this.products = products; }
}