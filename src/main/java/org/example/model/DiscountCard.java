package org.example.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "discount_value", precision = 5, scale = 2, nullable = false)
    private Double discountValue;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
