package org.example.persistence.dao.jpa.impl;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.persistence.TestConfig;
import org.example.persistence.dao.jpa.BookJpaDao;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {TestConfig.class})
class BookJpaDaoImplTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private BookJpaDao bookJpaDao;

    @Test
    void testInsert() {
        BookJpaEntity newBook = new BookJpaEntity(
                null,
                "1234567890123",
                "New Book Title",
                BigDecimal.valueOf(29.99),
                BigDecimal.valueOf(10)
        );

        String sql = "SELECT COUNT(b) FROM BookJpaEntity b";
        long countBefore = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        BookJpaEntity result = bookJpaDao.insert(newBook);

        long countAfter = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        long lastId = entityManager.createQuery("SELECT MAX(b.id) FROM BookJpaEntity b", Long.class)
                .getSingleResult();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(lastId, result.getId()),
                () -> assertEquals(newBook.getIsbn(), result.getIsbn()),
                () -> assertEquals(newBook.getTitle(), result.getTitle()),
                () -> assertEquals(newBook.getBasePrice(), result.getBasePrice()),
                () -> assertEquals(newBook.getDiscountPercentage(), result.getDiscountPercentage()),
                () -> assertEquals(countBefore + 1, countAfter)
        );
    }



}