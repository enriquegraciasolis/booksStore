package org.example.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.persistence.dao.jpa.BookJpaDao;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.List;
import java.util.Optional;

@Transactional
public class BookJpaDaoImpl implements BookJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookJpaEntity> findAll(int page, int size) {
        return entityManager.createQuery("SELECT b FROM BookJpaEntity b", BookJpaEntity.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<BookJpaEntity> findById(Long id) {
        BookJpaEntity book = entityManager.find(BookJpaEntity.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        entityManager.createQuery("DELETE FROM BookJpaEntity b WHERE b.isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }

    @Override
    public BookJpaEntity insert(BookJpaEntity bookJpaEntity) {
        entityManager.persist(bookJpaEntity);
        return bookJpaEntity;
    }

    @Override
    public BookJpaEntity update(BookJpaEntity bookJpaEntity) {
        return entityManager.merge(bookJpaEntity);
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM BookJpaEntity b", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<BookJpaEntity> findByIsbn(String isbn) {
        List<BookJpaEntity> results = entityManager.createQuery("SELECT b FROM BookJpaEntity b WHERE b.isbn = :isbn", BookJpaEntity.class)
                .setParameter("isbn", isbn)
                .getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }
}
