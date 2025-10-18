package org.example.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coffee_orders")
@Data
public class CoffeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coffee_type_id", nullable = false)
    private CoffeeType coffeeType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "waiter_name")
    private String waiterName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    private String status;
}