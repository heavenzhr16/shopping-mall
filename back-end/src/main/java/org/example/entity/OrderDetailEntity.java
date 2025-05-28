package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "order_detail")
@IdClass(OrderDetailEntity.OrderDetailId.class)
public class OrderDetailEntity {
    @Id
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_cnt")
    private Long productCnt;

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrdersEntity order;

    // 복합키 클래스
    public static class OrderDetailId implements Serializable {
        private Long orderDetailId;
        private Long orderId;

        public OrderDetailId() {}

        public OrderDetailId(Long orderDetailId, Long orderId) {
            this.orderDetailId = orderDetailId;
            this.orderId = orderId;
        }

        // equals, hashCode 구현
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderDetailId that = (OrderDetailId) o;
            return orderDetailId.equals(that.orderDetailId) && orderId.equals(that.orderId);
        }

        @Override
        public int hashCode() {
            return orderDetailId.hashCode() + orderId.hashCode();
        }

        // Getters and Setters
        public Long getOrderDetailId() { return orderDetailId; }
        public void setOrderDetailId(Long orderDetailId) { this.orderDetailId = orderDetailId; }

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
    }

    // 기본 생성자
    public OrderDetailEntity() {}

}