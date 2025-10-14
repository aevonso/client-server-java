package org.example.model;


import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "dessert_orders")
@Data
public class DessertOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dessert_id", nullable = false)
    private Dessert dessert;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    private String status;
}
