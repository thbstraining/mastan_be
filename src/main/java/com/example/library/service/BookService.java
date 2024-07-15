package com.example.library.service;

import java.util.List;

import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;

public interface BookService {

	Book createBook(Book book) throws BookNotFoundException;

	Book getBookById(long bookId) throws BookNotFoundException;
	
	Book getBookByName(String bookName) throws BookNotFoundException;

	Book getBookByAuthorName(String authorName) throws BookNotFoundException;

	List<Book> getAllBooks() throws BookNotFoundException;

	Boolean deleteBookById(long bookId) throws BookNotFoundException;

	Boolean deleteBookByName(String bookName) throws BookNotFoundException;


	Book updateBookById(Book book, long id) throws BookNotFoundException;


}
