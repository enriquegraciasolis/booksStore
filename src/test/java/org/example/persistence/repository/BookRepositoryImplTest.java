package org.example.persistence.repository;

import org.example.persistence.dao.jpa.BookJpaDao;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;
import org.example.persistence.dao.jpa.impl.BookJpaDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.persistence.repository.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {
    @Mock
    private BookJpaDaoImpl bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Test
    void findAll() {
        List<BookJpaEntity> bookList = List.of(
                new BookJpaEntity(1L, "1234567890123", "Book One", null, null),
                new BookJpaEntity(2L, "1234567890124", "Book Two", null, null),
                new BookJpaEntity(3L, "1234567890125", "Book Three", null, null)
        );

        when(bookJpaDao.findAll(1, 3)).thenReturn(bookList);
        when(bookJpaDao.count()).thenReturn(3L);

        var result = bookRepository.findAll(1, 3);

        assertEquals(1, result.pageNumber());
        assertEquals(3, result.pageSize());
        assertEquals(3, result.totalElements());
    }

    @Test
    void findById() {
        BookJpaEntity bookJpaEntity = new BookJpaEntity(1L, "1234567890123", "Book One", null, null);

        when(bookJpaDao.findById(1L)).thenReturn(Optional.of(bookJpaEntity));

        var result = bookRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals("1234567890123", result.get().isbn());
        assertEquals("Book One", result.get().title());
    }

    @Test
    void deleteByIsbn() {
        String isbn = "1234567890123";
        bookRepository.deleteByIsbn(isbn);
    }

    @Test
    void save() {
        BookJpaEntity savedEntity = new BookJpaEntity(1L, "1234567890123", "Book One", null, null);

        when(bookJpaDao.insert(any(BookJpaEntity.class))).thenReturn(savedEntity);

        var bookEntity = BookMapper.getInstance()
                .bookJpaEntityToBookEntity(new BookJpaEntity(null, "1234567890123", "Book One", null, null));

        var result = bookRepository.save(bookEntity);

        assertEquals(1L, result.id());
        assertEquals("1234567890123", result.isbn());
        assertEquals("Book One", result.title());
    }

    @Test
    void findByIsbn() {
        BookJpaEntity bookJpaEntity = new BookJpaEntity(1L, "1234567890123", "Book One", null, null);

        when(bookJpaDao.findByIsbn("1234567890123")).thenReturn(Optional.of(bookJpaEntity));

        var result = bookRepository.findByIsbn("1234567890123");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals("1234567890123", result.get().isbn());
        assertEquals("Book One", result.get().title());
    }
}