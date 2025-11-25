package org.example.domain.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record BookDto(
        Long id,
        @NotNull
        @Size(min = 13, max = 13, message = "ISBN must be 13 characters long")
        String isbn,
        @NotBlank
        String title,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "Base price must be greater than zero")
        BigDecimal basePrice,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "Discount percentage must be greater than zero")
        @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be less than or equal to 100")
        BigDecimal discountPercentage,
        BigDecimal price
) {
    public BookDto(Long id, String isbn, String title,
                   BigDecimal basePrice, BigDecimal discountPercentage,
                   BigDecimal price) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.price = price;
    }
}
