package org.example.persistence.repository;

import org.example.domain.model.Page;
import org.example.domain.repository.BookRepository;
import org.example.domain.repository.entity.BookEntity;
import org.example.persistence.dao.jpa.BookJpaDao;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;
import org.example.persistence.repository.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {
    private final BookJpaDao bookJpaDao;

    public BookRepositoryImpl(BookJpaDao bookJpaDao) {
        this.bookJpaDao = bookJpaDao;
    }

    public Page<BookEntity> findAll(int page, int size) {
        List<BookEntity> content = bookJpaDao.findAll(page, size).stream()
                .map(BookMapper.getInstance()::bookJpaEntityToBookEntity)
                .toList();
        long totalElements = bookJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    public Optional<BookEntity> findById(Long id) {
        return bookJpaDao.findById(id)
                .map(BookMapper.getInstance()::bookJpaEntityToBookEntity);
    }

    public void deleteByIsbn(String isbn) {
        bookJpaDao.deleteByIsbn(isbn);
    }

    public BookEntity save(BookEntity bookEntity) {
        var bookJpaEntity = BookMapper.getInstance().bookEntityToBookJpaEntity(bookEntity);
        BookJpaEntity savedEntity;
        if (bookEntity.id() == null) {
            savedEntity = bookJpaDao.insert(bookJpaEntity);
        } else {
            savedEntity = bookJpaDao.update(bookJpaEntity);
        }
        return BookMapper.getInstance().bookJpaEntityToBookEntity(savedEntity);
    }

    public Optional<BookEntity> findByIsbn(String isbn) {
        return bookJpaDao.findByIsbn(isbn)
                .map(BookMapper.getInstance()::bookJpaEntityToBookEntity);
    }
}
