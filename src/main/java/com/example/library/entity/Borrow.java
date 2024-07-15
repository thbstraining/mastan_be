package com.example.library.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Entity
public class Borrow {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="book_id")
    @NotNull(message = "Book must be specified")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User must be specified")
    private User user;

    @NotNull(message = "Borrow date must be specified")
    private LocalDate borrowDate;

    @NotNull(message = "Due date must be specified")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;


	public Borrow(Long id, @NotNull(message = "Book must be specified") Book book,
			@NotNull(message = "User must be specified") User user,
			@NotNull(message = "Borrow date must be specified") LocalDate borrowDate,
			@NotNull(message = "Due date must be specified") @Future(message = "Due date must be in the future") LocalDate dueDate) {
		super();
		this.id = id;
		this.book = book;
		this.user = user;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
    

	

}
