package org.example.presentation.controller;

import org.example.domain.model.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.example.domain.service.BookService;
import org.example.domain.service.dto.BookDto;
import org.example.presentation.controller.mapper.*;
import org.example.presentation.controller.webModel.request.BookInsertRequest;
import org.example.presentation.controller.webModel.request.BookUpdateRequest;
import org.example.presentation.controller.webModel.response.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void testFindAllBooks() throws Exception {
        List<BookDto> mockBookDtos = List.of(
                new BookDto(1L, "1234567890", "Test Book 1",
                        BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90)),
                new BookDto(2L, "0987654321", "Test Book 2",
                        BigDecimal.valueOf(150), BigDecimal.valueOf(15), BigDecimal.valueOf(127.5))
        );

        Page<BookDto> mockPage = new Page<>(
                mockBookDtos,
                1,
                10,
                2
        );

        when(bookService.getAll(1, 10)).thenReturn(mockPage);

        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .param("page", "1")
                        .param("size", "10"))
                .andReturn()
                .getResponse();

        mockMvc.perform(get("/api/books?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].isbn").value("1234567890"))
                .andExpect(jsonPath("$.data[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$.data[0].basePrice").value(100))
                .andExpect(jsonPath("$.data[0].discountPercentage").value(10))
                .andExpect(jsonPath("$.data[0].price").value(90))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].isbn").value("0987654321"))
                .andExpect(jsonPath("$.data[1].title").value("Test Book 2"))
                .andExpect(jsonPath("$.data[1].basePrice").value(150))
                .andExpect(jsonPath("$.data[1].discountPercentage").value(15))
                .andExpect(jsonPath("$.data[1].price").value(127.5));
    }

    @Test
    void testGetBookByIsbn() throws Exception {
        String isbn = "1234567890";
        BookDto mockBookDto = new BookDto(1L, isbn, "Test Book",
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(90));

        when(bookService.getByIsbn(isbn)).thenReturn(mockBookDto);

        mockMvc.perform(get("/api/books/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.basePrice").value(100))
                .andExpect(jsonPath("$.discountPercentage").value(10))
                .andExpect(jsonPath("$.price").value(90));
    }

    @Test
    void testCreateBook() throws Exception {
        BookInsertRequest bookInsertRequest = new BookInsertRequest(
                "1234567890",
                "New Book",
                BigDecimal.valueOf(120),
                BigDecimal.valueOf(12)
        );

        BookDto mockCreatedBookDto = new BookDto(1L, "1234567890", "New Book",
                BigDecimal.valueOf(120), BigDecimal.valueOf(12), BigDecimal.valueOf(105.6));

        when(bookService.create(org.mockito.ArgumentMatchers.any(BookDto.class))).thenReturn(mockCreatedBookDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "1234567890",
                                    "title": "New Book",
                                    "basePrice": 120,
                                    "discountPercentage": 12
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.basePrice").value(120))
                .andExpect(jsonPath("$.discountPercentage").value(12))
                .andExpect(jsonPath("$.price").value(105.6));
    }

    @Test
    void testUpdateBook() throws Exception {
        Long bookId = 1L;
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                bookId,
                "1234567890",
                "Updated Book",
                BigDecimal.valueOf(130),
                BigDecimal.valueOf(13)
        );
        BookDto mockUpdatedBookDto = new BookDto(bookId, "1234567890", "Updated Book",
                BigDecimal.valueOf(130), BigDecimal.valueOf(13), BigDecimal.valueOf(113.1));

        when(bookService.update(org.mockito.ArgumentMatchers.any(BookDto.class))).thenReturn(mockUpdatedBookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "1234567890",
                                    "title": "Updated Book",
                                    "basePrice": 130,
                                    "discountPercentage": 13
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.basePrice").value(130))
                .andExpect(jsonPath("$.discountPercentage").value(13))
                .andExpect(jsonPath("$.price").value(113.1));
    }

    @Test
    void testDeleteBook() throws Exception {
        String isbn = "1234567890";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{isbn}", isbn))
                .andExpect(status().isNoContent());
    }
}