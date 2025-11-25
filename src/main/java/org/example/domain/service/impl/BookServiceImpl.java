package org.example.domain.service.impl;

import org.example.domain.mapper.BookMapper;
import org.example.domain.model.Page;
import org.example.domain.repository.BookRepository;
import org.example.domain.repository.entity.BookEntity;
import org.example.domain.service.BookService;
import org.example.domain.service.dto.BookDto;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookDto> getAll(int page, int size) {
        Page<BookEntity> bookEntityPage = bookRepository.findAll(page, size);
        List<BookDto> bookDtos = bookEntityPage.data().stream()
                .map(bookEntity -> BookMapper.getInstance()
                        .bookToBookDto(BookMapper.getInstance()
                                .bookEntityToBook(bookEntity)))
                .toList();
        return new Page<>(
                bookDtos,
                bookEntityPage.pageNumber(),
                bookEntityPage.pageSize(),
                bookEntityPage.totalElements()
        );
    }

    public BookDto getByIsbn(String isbn) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookMapper.getInstance()
                .bookToBookDto(BookMapper.getInstance()
                        .bookEntityToBook(bookEntity));
    }

    public Optional<BookDto> findByIsbn(String isbn) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return Optional.ofNullable(BookMapper.getInstance()
                .bookToBookDto(BookMapper.getInstance()
                        .bookEntityToBook(bookEntity)));
    }

    public BookDto create(BookDto bookDto) {
        BookEntity bookEntity = BookMapper.getInstance()
                .bookToBookEntity(BookMapper.getInstance()
                        .bookDtoToBook(bookDto));
        BookEntity savedEntity = bookRepository.save(bookEntity);
        return BookMapper.getInstance()
                .bookToBookDto(BookMapper.getInstance()
                        .bookEntityToBook(savedEntity));
    }

    public BookDto update(BookDto bookDto) {
        BookEntity existingEntity = bookRepository.findById(bookDto.id())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        BookEntity bookEntityToUpdate = BookMapper.getInstance()
                .bookToBookEntity(BookMapper.getInstance()
                        .bookDtoToBook(bookDto));
        BookEntity updatedEntity = bookRepository.save(bookEntityToUpdate);
        return BookMapper.getInstance()
                .bookToBookDto(BookMapper.getInstance()
                        .bookEntityToBook(updatedEntity));
    }

    public void deleteByIsbn(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }
}
