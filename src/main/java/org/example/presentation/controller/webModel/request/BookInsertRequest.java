package org.example.presentation.controller.webModel.request;

import java.math.BigDecimal;

public record BookInsertRequest(
        String isbn,
        String title,
        BigDecimal basePrice,
        BigDecimal discountPercentage
) {
}
