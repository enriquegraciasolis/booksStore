package org.example.persistence.repository.mapper;

import org.example.domain.repository.entity.BookEntity;
import org.example.persistence.dao.jpa.entity.BookJpaEntity;

public class BookMapper {
    private static BookMapper INSTANCE;

    private BookMapper() {}

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    public BookEntity bookJpaEntityToBookEntity(BookJpaEntity bookJpaEntity) {
        if (bookJpaEntity == null) {
            return null;
        }

        return new BookEntity(
                bookJpaEntity.getId(),
                bookJpaEntity.getIsbn(),
                bookJpaEntity.getTitle(),
                bookJpaEntity.getBasePrice(),
                bookJpaEntity.getDiscountPercentage()
        );
    }

    public BookJpaEntity bookEntityToBookJpaEntity(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        return new BookJpaEntity(
                bookEntity.id(),
                bookEntity.isbn(),
                bookEntity.title(),
                bookEntity.basePrice(),
                bookEntity.discountPercentage()
        );
    }
}
