package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "colors")
public class ColorEntity {

    // Getter/Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    // 양방향 ManyToMany 매핑
    @ManyToMany(mappedBy = "colors")
    private List<ProductsEntity> products;

    public ColorEntity() {}

}
