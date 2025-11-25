package org.example.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class BookJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    @Column(name = "title")
    private String title;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    public BookJpaEntity() {}

    public BookJpaEntity(Long id, String isbn, String title, BigDecimal basePrice, BigDecimal discountPercentage) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }
}
