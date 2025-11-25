package org.example.presentation.controller.webModel.response;

import java.math.BigDecimal;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        BigDecimal price
) {
}
