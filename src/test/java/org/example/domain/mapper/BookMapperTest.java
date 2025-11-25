package org.example.domain.mapper;

import org.example.domain.model.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void testBookToBookDto() {
        Book book = new Book(1L, "1234567890", "Test Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));

        var bookDto = BookMapper.getInstance().bookToBookDto(book);

        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getIsbn(), bookDto.isbn());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getBasePrice(), bookDto.basePrice());
        assertEquals(book.getDiscountPercentage(), bookDto.discountPercentage());
        assertEquals(book.getPrice(), bookDto.price());
    }

    @Test
    void testBookDtoToBook() {
        var bookDto = new org.example.domain.service.dto.BookDto(1L, "1234567890", "Test Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        Book book = BookMapper.getInstance().bookDtoToBook(bookDto);

        assertNotNull(book);
        assertEquals(bookDto.id(), book.getId());
        assertEquals(bookDto.isbn(), book.getIsbn());
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.basePrice(), book.getBasePrice());
        assertEquals(bookDto.discountPercentage(), book.getDiscountPercentage());
        assertEquals(BigDecimal.valueOf(90), book.getPrice());
    }

    @Test
    void testBookToBookEntity() {
        Book book = new Book(1L, "1234567890", "Test Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));

        var bookEntity = BookMapper.getInstance().bookToBookEntity(book);

        assertNotNull(bookEntity);
        assertEquals(book.getId(), bookEntity.id());
        assertEquals(book.getIsbn(), bookEntity.isbn());
        assertEquals(book.getTitle(), bookEntity.title());
        assertEquals(book.getBasePrice(), bookEntity.basePrice());
        assertEquals(book.getDiscountPercentage(), bookEntity.discountPercentage());
    }

    @Test
    void testBookEntityToBook() {
        var bookEntity = new org.example.domain.repository.entity.BookEntity(1L, "1234567890", "Test Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));

        Book book = BookMapper.getInstance().bookEntityToBook(bookEntity);
        assertNotNull(book);
        assertEquals(bookEntity.id(), book.getId());
        assertEquals(bookEntity.isbn(), book.getIsbn());
        assertEquals(bookEntity.title(), book.getTitle());
        assertEquals(bookEntity.basePrice(), book.getBasePrice());
        assertEquals(bookEntity.discountPercentage(), book.getDiscountPercentage());
        assertEquals(BigDecimal.valueOf(90), book.getPrice());
    }
}