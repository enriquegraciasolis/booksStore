package org.example.domain.repository.entity;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record BookEntity(
        Long id,
        String isbn,
        String title,
        BigDecimal basePrice,
        BigDecimal discountPercentage
) {
    public BookEntity(Long id, String isbn, String title,
                      BigDecimal basePrice, BigDecimal discountPercentage) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
    }
}
