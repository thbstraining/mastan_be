package com.example.library.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @NotNull(message = "Title must be specified")
    @NotEmpty(message = "Title must be specified")
    private String title;

    @NotNull(message = "Author must be specified")
    @NotEmpty(message = "Author must be specified")
    private String author;

    @NotNull(message = "ISBN must be specified")
    @NotEmpty(message = "ISBN must be specified")
    private String isbn;

    @NotNull(message = "Price must be specified")
    private Double price;

    @NotNull(message = "Published date must be specified")
    private LocalDate publishedDate;

    @NotNull(message = "Genre must be specified")
    @NotEmpty(message = "Genre must be specified")
    private String genre;

    @NotNull(message = "Copies must be specified")
    private int copies;

    @NotNull(message = "Available must be specified")
    private boolean available;

	public Book(long bookId,
			@NotNull(message = "Title must be specified") @NotEmpty(message = "Title must be specified") String title,
			@NotNull(message = "Author must be specified") @NotEmpty(message = "Author must be specified") String author,
			@NotNull(message = "ISBN must be specified") @NotEmpty(message = "ISBN must be specified") String isbn,
			@NotNull(message = "Price must be specified") Double price,
			@NotNull(message = "Published date must be specified") LocalDate publishedDate,
			@NotNull(message = "Genre must be specified") @NotEmpty(message = "Genre must be specified") String genre,
			@NotNull(message = "Copies must be specified") int copies,
			@NotNull(message = "Available must be specified") boolean available) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.publishedDate = publishedDate;
		this.genre = genre;
		this.copies = copies;
		this.available = available;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
