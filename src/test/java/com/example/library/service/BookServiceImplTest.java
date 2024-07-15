package com.example.library.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book(1,"Spring in Action", "Craig Walls", "978-1617294945", 39.99,
                LocalDate.of(2022, 1, 1), "Technical", 10, true);
        testBook.setBookId(1L);
    }

    @Test
    void testGetBookByName_Success() {
        when(bookRepository.findByTitle(anyString())).thenReturn(testBook);

        try {
            Book foundBook = bookService.getBookByName("Spring in Action");
            assertEquals(testBook.getBookId(), foundBook.getBookId());
            assertEquals(testBook.getTitle(), foundBook.getTitle());
            assertEquals(testBook.getAuthor(), foundBook.getAuthor());
            assertEquals(testBook.getIsbn(), foundBook.getIsbn());
            assertEquals(testBook.getPrice(), foundBook.getPrice());
            assertEquals(testBook.getPublishedDate(), foundBook.getPublishedDate());
            assertEquals(testBook.getGenre(), foundBook.getGenre());
            assertEquals(testBook.getCopies(), foundBook.getCopies());
            assertEquals(testBook.isAvailable(), foundBook.isAvailable());
        } catch (BookNotFoundException e) {
            fail("Unexpected BookNotFoundException thrown");
        }
    }

    @Test
    void testGetBookByName_BookNotFound() {
        when(bookRepository.findByTitle(anyString())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByName("Non-existent Book");
        });
    }


    @Test
    void testGetBookByAuthorName_Success() {
        when(bookRepository.findByAuthor(anyString())).thenReturn(testBook);

        try {
            Book foundBook = bookService.getBookByAuthorName("Craig Walls");
            assertEquals(testBook.getBookId(), foundBook.getBookId());
            assertEquals(testBook.getTitle(), foundBook.getTitle());
            assertEquals(testBook.getAuthor(), foundBook.getAuthor());
            assertEquals(testBook.getIsbn(), foundBook.getIsbn());
            assertEquals(testBook.getPrice(), foundBook.getPrice());
            assertEquals(testBook.getPublishedDate(), foundBook.getPublishedDate());
            assertEquals(testBook.getGenre(), foundBook.getGenre());
            assertEquals(testBook.getCopies(), foundBook.getCopies());
            assertEquals(testBook.isAvailable(), foundBook.isAvailable());
        } catch (BookNotFoundException e) {
            fail("Unexpected BookNotFoundException thrown");
        }
    }

    @Test
    void testGetBookByAuthorName_BookNotFound() {
        when(bookRepository.findByAuthor(anyString())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByAuthorName("Non-existent Author");
        });
    }

    @Test
    void testGetAllBooks_Success() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(testBook);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        try {
            List<Book> foundBooks = bookService.getAllBooks();
            assertFalse(foundBooks.isEmpty());
            assertEquals(1, foundBooks.size());
            Book foundBook = foundBooks.get(0);
            assertEquals(testBook.getBookId(), foundBook.getBookId());
            assertEquals(testBook.getTitle(), foundBook.getTitle());
            assertEquals(testBook.getAuthor(), foundBook.getAuthor());
            assertEquals(testBook.getIsbn(), foundBook.getIsbn());
            assertEquals(testBook.getPrice(), foundBook.getPrice());
            assertEquals(testBook.getPublishedDate(), foundBook.getPublishedDate());
            assertEquals(testBook.getGenre(), foundBook.getGenre());
            assertEquals(testBook.getCopies(), foundBook.getCopies());
            assertEquals(testBook.isAvailable(), foundBook.isAvailable());
        } catch (BookNotFoundException e) {
            fail("Unexpected BookNotFoundException thrown");
        }
    }

    @Test
    void testGetAllBooks_NoBooksFound() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getAllBooks();
        });
    }


    

    @Test
    void testUpdateBookById_BookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookById(testBook, 1L);
        });
    }


    @Test
    void testDeleteBookById_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(testBook));

        try {
            boolean result = bookService.deleteBookById(1L);
            assertTrue(result);
        } catch (BookNotFoundException e) {
            fail("Unexpected BookNotFoundException thrown");
        }
    }

    @Test
    void testDeleteBookById_BookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBookById(1L);
        });
    }


    @Test
    void testDeleteBookByName_Success() {
        when(bookRepository.findByTitle(anyString())).thenReturn(testBook);

        try {
            boolean result = bookService.deleteBookByName("Spring in Action");
            assertTrue(result);
        } catch (BookNotFoundException e) {
            fail("Unexpected BookNotFoundException thrown");
        }
    }

    @Test
    void testDeleteBookByName_BookNotFound() {
        when(bookRepository.findByTitle(anyString())).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBookByName("Non-existent Book");
        });
    }
}
