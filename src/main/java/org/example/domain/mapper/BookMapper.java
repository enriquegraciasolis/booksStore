package org.example.domain.mapper;

import org.example.domain.model.Book;
import org.example.domain.repository.entity.BookEntity;
import org.example.domain.service.dto.BookDto;

public class BookMapper {
    private static BookMapper INSTANCE;

    private BookMapper() {}

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    public BookDto bookToBookDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getBasePrice(),
                book.getDiscountPercentage(),
                book.getPrice()
        );
    }

    public Book bookDtoToBook(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        return new Book(
                bookDto.id(),
                bookDto.isbn(),
                bookDto.title(),
                bookDto.basePrice(),
                bookDto.discountPercentage()
        );
    }

    public BookEntity bookToBookEntity(Book book) {
        if (book == null) {
            return null;
        }

        return new BookEntity(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getBasePrice(),
                book.getDiscountPercentage()
        );
    }

    public Book bookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        return new Book(
                bookEntity.id(),
                bookEntity.isbn(),
                bookEntity.title(),
                bookEntity.basePrice(),
                bookEntity.discountPercentage()
        );
    }
}
