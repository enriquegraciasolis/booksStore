package org.example.presentation.controller.webModel.request;

import java.math.BigDecimal;

public record BookUpdateRequest(
        Long id,
        String isbn,
        String title,
        BigDecimal basePrice,
        BigDecimal discountPercentage
) {
}
