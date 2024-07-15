package com.example.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) throws BookNotFoundException {
        try {
            if (book != null) {
                return bookRepository.save(book);
                
            }
            
               throw new BookNotFoundException("Empty request body. Please provide book details.");
 
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException("Invalid book details provided");
        }
    
     
    }

    @Override
    public Book getBookById(long bookId) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
    }

    @Override
    public Book getBookByName(String bookName) throws BookNotFoundException {
        Book book = bookRepository.findByTitle(bookName);
        if (book == null) {
            throw new BookNotFoundException("Book not found with title: " + bookName);
        }
        return book;
    }

    @Override
    public Book getBookByAuthorName(String authorName) throws BookNotFoundException {
        Book book = bookRepository.findByAuthor(authorName);
        if (book == null) {
            throw new BookNotFoundException("Book not found with author: " + authorName);
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() throws BookNotFoundException {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found in the database");
        }
        return books;
    }

    @Override
    public Book updateBookById(Book book, long bookId) throws BookNotFoundException {
        Book oldBook = getBookById(bookId);
        if (oldBook != null) {
        	book.setBookId(bookId);
            oldBook.setAuthor(book.getAuthor());
            oldBook.setCopies(book.getCopies());
            oldBook.setTitle(book.getTitle());
            oldBook.setPrice(book.getPrice());
            return bookRepository.save(oldBook);
        }
        throw new BookNotFoundException("Book not found with id: " + bookId);
    }

    @Override
    public Boolean deleteBookById(long bookId) throws BookNotFoundException {
        Book book = getBookById(bookId);
        if (book != null) {
            bookRepository.delete(book);
            return true;
        }
        throw new BookNotFoundException("Book not found with id: " + bookId);
    }

    @Override
    public Boolean deleteBookByName(String bookName) throws BookNotFoundException {
        Book book = bookRepository.findByTitle(bookName);
        if (book != null) {
            bookRepository.delete(book);
            return true;
        }
        throw new BookNotFoundException("Book not found with title: " + bookName);
    }
}

