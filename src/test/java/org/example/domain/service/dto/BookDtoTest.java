package org.example.domain.service.dto;

import org.example.domain.exception.ValidationException;
import org.example.domain.validation.DtoValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    @Test
    void testValidBookDto() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Valid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));
        assertDoesNotThrow(() -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_NullId() {
        BookDto bookDto = new BookDto(null, "1234567890123", "Invalid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_BlankTitle() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "   ",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_NullIsbn() {
        BookDto bookDto = new BookDto(1L, null, "Invalid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_InvalidIsbn() {
        BookDto bookDto = new BookDto(1L, "12345", "Invalid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_NegativeBasePrice() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Invalid Book",
                BigDecimal.valueOf(-100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));
        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_BasePriceNotNull() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Invalid Book",
                null, BigDecimal.valueOf(10), BigDecimal.valueOf(90));
        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_DiscountPercentageNull() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Invalid Book",
                BigDecimal.valueOf(100), null, BigDecimal.valueOf(90));
        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_DiscountPercentageOutOfRange() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Invalid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(150), BigDecimal.valueOf(90));
        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }

    @Test
    void testInvalidBookDto_DiscountPercentageMinValue() {
        BookDto bookDto = new BookDto(1L, "1234567890123", "Invalid Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(-10), BigDecimal.valueOf(90));
        assertThrows(ValidationException.class, () -> DtoValidator.validate(bookDto));
    }
}