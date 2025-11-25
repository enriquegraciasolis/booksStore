package org.example.presentation.controller.mapper;

import org.example.domain.service.dto.BookDto;
import org.example.presentation.controller.webModel.request.BookInsertRequest;
import org.example.presentation.controller.webModel.request.BookUpdateRequest;
import org.example.presentation.controller.webModel.response.BookResponse;

public class BookMapper {
    private static BookMapper INSTANCE;

    private BookMapper() {}

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    public BookResponse bookDtoToBookResponse(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        return new BookResponse(
                bookDto.id(),
                bookDto.isbn(),
                bookDto.title(),
                bookDto.basePrice(),
                bookDto.discountPercentage(),
                bookDto.price()
        );
    }

    public BookDto bookInsertRequestToBookDto(BookInsertRequest bookInsertRequest) {
        return new BookDto(
                null,
                bookInsertRequest.isbn(),
                bookInsertRequest.title(),
                bookInsertRequest.basePrice(),
                bookInsertRequest.discountPercentage(),
                null
        );
    }

    public BookDto bookUpdateRequestToBookDto(BookUpdateRequest bookUpdateRequest) {
        return new BookDto(
                bookUpdateRequest.id(),
                bookUpdateRequest.isbn(),
                bookUpdateRequest.title(),
                bookUpdateRequest.basePrice(),
                bookUpdateRequest.discountPercentage(),
                null
        );
    }
}
