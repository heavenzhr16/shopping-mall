package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class OrdersEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "totalPrice")
    private Long totalPrice;

    @Column(name = "orderdAt")
    private LocalDateTime orderedAt;

    @Column(name = "paidAt")
    private LocalDateTime paidAt;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails;

    // 기본 생성자
    public OrdersEntity() {}

}