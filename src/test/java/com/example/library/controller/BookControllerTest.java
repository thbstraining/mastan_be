package com.example.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.library.controller.BookController;
import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.service.BookService;
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testCreateBook() throws BookNotFoundException {
       
        Book book = new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        ResponseEntity<?> responseEntity = bookController.createBook(book);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }
    @Test
    public void testGetBookById() throws BookNotFoundException {
       
        long bookId = 1L;
        Book book = new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        when(bookService.getBookById(bookId)).thenReturn(book);
        ResponseEntity<?> responseEntity = bookController.getBookById(bookId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    public void testGetBookByAuthor() throws BookNotFoundException {
        
        String authorName = "Craig Walls";
        Book book = new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        when(bookService.getBookByAuthorName(authorName)).thenReturn(book);
        ResponseEntity<?> responseEntity = bookController.getBookByAuthor(authorName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    public void testGetBookByBookName() throws BookNotFoundException {
     
        String bookName = "Spring in Action";
        Book book = new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        when(bookService.getBookByName(bookName)).thenReturn(book);
        ResponseEntity<?> responseEntity = bookController.getBookByBookName(bookName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    public void testGetBooks() throws BookNotFoundException {
        List<Book> books = Arrays.asList(
            new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                    LocalDate.of(2022, 1, 1), "Technical", 10, true),
           new Book(2, "Spring in ", "Craig Walls", "978-1617294945", 39.99,
                            LocalDate.of(2022, 1, 1), "Technical", 10, true)
        );
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity<?> responseEntity = bookController.getBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }
    @Test
    public void testUpdateBook() throws BookNotFoundException {

        long bookId = 1L;
        Book book = new Book(1, "Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        when(bookService.updateBookById(any(Book.class), eq(bookId))).thenReturn(book);
        ResponseEntity<?> responseEntity = bookController.updateBook(book, bookId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    public void testDeleteBookById() throws BookNotFoundException {

        long bookId = 1L;
        when(bookService.deleteBookById(bookId)).thenReturn(true);
        ResponseEntity<?> responseEntity = bookController.deleteBookById(bookId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Book deleted successfully", responseEntity.getBody());
    }

    @Test
    public void testDeleteBookByName() throws BookNotFoundException {

        String bookName = "Spring in Action";
        when(bookService.deleteBookByName(bookName)).thenReturn(true);

        ResponseEntity<?> responseEntity = bookController.deleteBookByName(bookName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Book deleted successfully", responseEntity.getBody());
    }



}
