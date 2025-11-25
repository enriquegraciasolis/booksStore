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

    @Test
    void testUpdate() {
        BookJpaEntity existingBook = new BookJpaEntity(
                null,
                "9876543210987",
                "Existing Book Title",
                BigDecimal.valueOf(39.99),
                BigDecimal.valueOf(15)
        );

        entityManager.persist(existingBook);
        entityManager.flush();

        BookJpaEntity toUpdate = new BookJpaEntity(
                existingBook.getId(),
                "9876543210987",
                "Updated Book Title",
                BigDecimal.valueOf(34.99),
                BigDecimal.valueOf(20)
        );

        BookJpaEntity result = bookJpaDao.update(toUpdate);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(toUpdate.getId(), result.getId()),
                () -> assertEquals(toUpdate.getIsbn(), result.getIsbn()),
                () -> assertEquals(toUpdate.getTitle(), result.getTitle()),
                () -> assertEquals(toUpdate.getBasePrice(), result.getBasePrice()),
                () -> assertEquals(toUpdate.getDiscountPercentage(), result.getDiscountPercentage())
        );
    }

    @Test
    void testFindByIsbn() {
        BookJpaEntity book = new BookJpaEntity(
                null,
                "4444444444444",
                "Book To Find",
                BigDecimal.valueOf(24.99),
                BigDecimal.valueOf(12)
        );

        entityManager.persist(book);
        entityManager.flush();

        var result = bookJpaDao.findByIsbn("4444444444444");

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(book.getId(), result.get().getId()),
                () -> assertEquals(book.getIsbn(), result.get().getIsbn()),
                () -> assertEquals(book.getTitle(), result.get().getTitle()),
                () -> assertEquals(book.getBasePrice(), result.get().getBasePrice()),
                () -> assertEquals(book.getDiscountPercentage(), result.get().getDiscountPercentage())
        );
    }

    @Test
    void testCount() {
        BookJpaEntity book1 = new BookJpaEntity(
                null,
                "1111111111111",
                "First Book",
                BigDecimal.valueOf(14.99),
                BigDecimal.valueOf(7)
        );

        BookJpaEntity book2 = new BookJpaEntity(
                null,
                "2222222222222",
                "Second Book",
                BigDecimal.valueOf(22.99),
                BigDecimal.valueOf(11)
        );

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();

        long count = bookJpaDao.count();

        assertTrue(count >= 2);
    }

    @Test
    void testFindAll() {
        BookJpaEntity book1 = new BookJpaEntity(
                null,
                "3333333333333",
                "Third Book",
                BigDecimal.valueOf(18.99),
                BigDecimal.valueOf(9)
        );

        BookJpaEntity book2 = new BookJpaEntity(
                null,
                "6666666666666",
                "Fourth Book",
                BigDecimal.valueOf(27.99),
                BigDecimal.valueOf(14)
        );

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();

        var result = bookJpaDao.findAll(0, 10);

        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.size() >= 2)
        );

    }

    @Test
    void testFindById() {
        BookJpaEntity book = new BookJpaEntity(
                null,
                "7777777777777",
                "Fifth Book",
                BigDecimal.valueOf(31.99),
                BigDecimal.valueOf(16)
        );

        entityManager.persist(book);
        entityManager.flush();

        var result = bookJpaDao.findById(book.getId());

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(book.getId(), result.get().getId()),
                () -> assertEquals(book.getIsbn(), result.get().getIsbn()),
                () -> assertEquals(book.getTitle(), result.get().getTitle()),
                () -> assertEquals(book.getBasePrice(), result.get().getBasePrice()),
                () -> assertEquals(book.getDiscountPercentage(), result.get().getDiscountPercentage())
        );
    }

    @Test
    void testDelete() {

    }
}