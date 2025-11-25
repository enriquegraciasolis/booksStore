package org.example.domain.service.impl;

import org.example.domain.mapper.BookMapper;
import org.example.domain.model.Page;
import org.example.domain.repository.BookRepository;
import org.example.domain.repository.entity.BookEntity;
import org.example.domain.service.BookService;
import org.example.domain.service.dto.BookDto;
import org.example.persistence.repository.BookRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Page<BookEntity> bookEntityPage = new Page<>(
            java.util.List.of(
                    new BookEntity(1L, "1234567890123", "Book One",
                            BigDecimal.valueOf(100), BigDecimal.valueOf(10)),
                    new BookEntity(2L, "1234567890124", "Book Two",
                            BigDecimal.valueOf(150), BigDecimal.valueOf(15)),
                    new BookEntity(3L, "1234567890125", "Book Three",
                            BigDecimal.valueOf(200), BigDecimal.valueOf(20))
            ),
            1,
            3,
            3
    );

    private Page<BookDto> bookPageList = new Page<>(
            java.util.List.of(
                    new BookDto(1L, "1234567890123", "Book One",
                            BigDecimal.valueOf(100), BigDecimal.valueOf(10),
                            BigDecimal.valueOf(90)),
                    new BookDto(2L, "1234567890124", "Book Two",
                            BigDecimal.valueOf(150), BigDecimal.valueOf(15),
                            BigDecimal.valueOf(127.5)),
                    new BookDto(3L, "1234567890125", "Book Three",
                            BigDecimal.valueOf(200), BigDecimal.valueOf(20),
                            BigDecimal.valueOf(160))
            ),
            1,
            3,
            3
    );

    @Test
    void testGetAll() {
        when(bookRepository.findAll(1, 3)).thenReturn(bookEntityPage);

        Page<BookDto> result = bookService.getAll(1, 3);

        assertThat(result).isEqualTo(bookPageList);
    }

    @Test
    void testGetByIsbn() {
        BookEntity bookEntity = new BookEntity(1L, "1234567890123", "Book One",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));
        BookDto expectedDto = new BookDto(1L, "1234567890123", "Book One",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10),
                BigDecimal.valueOf(90));

        when(bookRepository.findByIsbn("1234567890123")).thenReturn(java.util.Optional.of(bookEntity));

        BookDto result = bookService.getByIsbn("1234567890123");

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void testFindByIsbn() {
        BookEntity bookEntity = new BookEntity(1L, "1234567890123", "Book One",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));
        BookDto expectedDto = new BookDto(1L, "1234567890123", "Book One",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10),
                BigDecimal.valueOf(90));

        when(bookRepository.findByIsbn("1234567890123")).thenReturn(java.util.Optional.of(bookEntity));

        java.util.Optional<BookDto> result = bookService.findByIsbn("1234567890123");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDto);
    }

    @Test
    void testCreate() {
        BookDto bookDtoToCreate = new BookDto(null, "1234567890126", "Book Four",
                BigDecimal.valueOf(120), BigDecimal.valueOf(12), null);
        BookEntity bookEntityToSave = new BookEntity(null, "1234567890126", "Book Four",
                BigDecimal.valueOf(120), BigDecimal.valueOf(12));
        BookEntity savedBookEntity = new BookEntity(4L, "1234567890126", "Book Four",
                BigDecimal.valueOf(120), BigDecimal.valueOf(12));
        BookDto expectedDto = new BookDto(4L, "1234567890126", "Book Four",
                BigDecimal.valueOf(120), BigDecimal.valueOf(12),
                BigDecimal.valueOf(105.6));

        when(bookRepository.save(bookEntityToSave)).thenReturn(savedBookEntity);

        BookDto result = bookService.create(bookDtoToCreate);

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void testUpdate() {
        BookDto bookDtoToUpdate = new BookDto(1L, "1234567890123", "Updated Book One",
                BigDecimal.valueOf(110), BigDecimal.valueOf(11), null);
        BookEntity existingBookEntity = new BookEntity(1L, "1234567890123", "Book One",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10));
        BookEntity bookEntityToSave = new BookEntity(1L, "1234567890123", "Updated Book One",
                BigDecimal.valueOf(110), BigDecimal.valueOf(11));
        BookEntity updatedBookEntity = new BookEntity(1L, "1234567890123", "Updated Book One",
                BigDecimal.valueOf(110), BigDecimal.valueOf(11));
        BookDto expectedDto = new BookDto(1L, "1234567890123", "Updated Book One",
                BigDecimal.valueOf(110), BigDecimal.valueOf(11),
                BigDecimal.valueOf(97.9));

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(existingBookEntity));
        when(bookRepository.save(bookEntityToSave)).thenReturn(updatedBookEntity);

        BookDto result = bookService.update(bookDtoToUpdate);

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void testDeleteByIsbn() {
        String isbnToDelete = "1234567890123";

        assertDoesNotThrow(() -> bookService.deleteByIsbn(isbnToDelete));
    }
}