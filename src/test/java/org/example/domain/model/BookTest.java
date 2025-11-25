package org.example.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @ParameterizedTest
    @CsvSource({
            "100, 10, 90",
            "200, 25, 150",
            "50, 0, 50",
            "80, -50, 80"
    })
    void testCalculateFinalPrice(BigDecimal basePrice, BigDecimal discountPercentage, BigDecimal expectedPrice) {
        Book book = new Book(1L, "1234567890", "Test Book", basePrice, discountPercentage);
        assertEquals(expectedPrice, book.getPrice());
    }

}