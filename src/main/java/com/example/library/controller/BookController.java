package com.example.library.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entity.Book;
import com.example.library.entity.Borrow;
import com.example.library.exception.BookNotFoundException;
import com.example.library.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookServiceImpl;

    @PostMapping("/create-book")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Book savedBook = bookServiceImpl.createBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid book details provided");
        }
       
    }

    @GetMapping("/user/id/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable long bookId) {
        try {
            Book book = bookServiceImpl.getBookById(bookId);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/author/{authorName}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable String authorName) {
        try {
            Book book = bookServiceImpl.getBookByAuthorName(authorName);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/name/{bookName}")
    public ResponseEntity<?> getBookByBookName(@PathVariable String bookName) {
        try {
            Book book = bookServiceImpl.getBookByName(bookName);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getBooks() {
        try {
            List<Book> books = bookServiceImpl.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/update/{bookId}")
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable long bookId) {
        try {
            Book updatedBook =bookServiceImpl.updateBookById(book, bookId);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid book details provided");
        }
    }


    @DeleteMapping("/deleteId/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable long bookId) {
        try {
            Boolean deleted = bookServiceImpl.deleteBookById(bookId);
            if (deleted) {
                return ResponseEntity.ok("Book deleted successfully");
            } 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with id: " + bookId);
            
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteName/{bookName}")
    public ResponseEntity<?> deleteBookByName(@PathVariable String bookName) {
        try {
            Boolean deleted = bookServiceImpl.deleteBookByName(bookName);
            if (deleted) {
                return ResponseEntity.ok("Book deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with title: " + bookName);
            }
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
