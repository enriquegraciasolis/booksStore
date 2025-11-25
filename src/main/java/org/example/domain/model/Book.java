package org.example.domain.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Book {
    private final Long id;
    private final String isbn;
    private final String title;
    private final BigDecimal basePrice;
    private final BigDecimal discountPercentage;
    private final BigDecimal price;

    public Book(Long id, String isbn, String title,
                BigDecimal basePrice, BigDecimal discountPercentage) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage.min(BigDecimal.valueOf(100)).max(BigDecimal.ZERO);
        this.price = calculateFinalPrice();
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

    public BigDecimal getPrice() {
        return price;
    }

    private BigDecimal calculateFinalPrice() {
        BigDecimal discountAmount = basePrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
        return basePrice.subtract(discountAmount);
    }
}
