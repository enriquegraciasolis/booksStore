package org.example.persistence.dao.jpa;

import org.example.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.List;
import java.util.Optional;

public interface BookJpaDao {
    List<BookJpaEntity> findAll(int page, int size);
    Optional<BookJpaEntity> findById(Long id);
    void deleteByIsbn(String isbn);
    BookJpaEntity insert(BookJpaEntity bookJpaEntity);
    BookJpaEntity update(BookJpaEntity bookJpaEntity);
    long count();
    Optional<BookJpaEntity> findByIsbn(String isbn);
}
