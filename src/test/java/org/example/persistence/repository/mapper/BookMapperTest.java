package org.example.persistence.repository.mapper;

import org.example.domain.repository.entity.BookEntity;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;
import org.example.persistence.repository.mapper.BookMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    @Test
    void testBookJpaEntityToBookEntity() {
        BookEntity bookEntity = BookMapper.getInstance().bookJpaEntityToBookEntity(
                new BookJpaEntity(
                        1L,
                        "978-3-16-148410-0",
                        "Sample Book",
                        new BigDecimal(20.99),
                        new BigDecimal(10.0)
                )
        );

        assertNotNull(bookEntity);
        assertEquals(1L, bookEntity.id());
        assertEquals("978-3-16-148410-0", bookEntity.isbn());
        assertEquals("Sample Book", bookEntity.title());
        assertEquals(new BigDecimal(20.99), bookEntity.basePrice());
        assertEquals(new BigDecimal(10.0), bookEntity.discountPercentage());
    }

    @Test
    void testBookEntityToBookJpaEntity() {
        BookJpaEntity bookJpaEntity = BookMapper.getInstance().bookEntityToBookJpaEntity(
                new BookEntity(
                        2L,
                        "978-1-23-456789-0",
                        "Another Book",
                        new BigDecimal(15.50),
                        new BigDecimal(5.0)
                )
        );

        assertNotNull(bookJpaEntity);
        assertEquals(2L, bookJpaEntity.getId());
        assertEquals("978-1-23-456789-0", bookJpaEntity.getIsbn());
        assertEquals("Another Book", bookJpaEntity.getTitle());
        assertEquals(new BigDecimal(15.50), bookJpaEntity.getBasePrice());
        assertEquals(new BigDecimal(5.0), bookJpaEntity.getDiscountPercentage());
    }
}