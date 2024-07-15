package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
import com.example.library.entity.Borrow;
import com.example.library.entity.User;
import com.example.library.exception.BorrowNotFoundException;
import com.example.library.repository.BorrowRepository;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;

    @InjectMocks
    private BorrowService borrowService;

    private Borrow testBorrow;

    @BeforeEach
    public void setUp() {
       
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");

        User user = new User();
        user.setUserName("sampleuser");

        testBorrow = new Borrow(1L, book, user, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    public void testCreateBorrow() {
        when(borrowRepository.save(any(Borrow.class))).thenReturn(testBorrow);

        Borrow createdBorrow = null;
        try {
            createdBorrow = borrowService.createborrow(testBorrow);
        } catch (BorrowNotFoundException e) {
            fail("Unexpected BorrowNotFoundException");
        }

        assertNotNull(createdBorrow);
        assertEquals(testBorrow.getId(), createdBorrow.getId());
        assertEquals(testBorrow.getBook().getTitle(), createdBorrow.getBook().getTitle());
        assertEquals(testBorrow.getUser().getUserName(), createdBorrow.getUser().getUserName());
    }

    @Test
    public void testGetBorrowById() {
        when(borrowRepository.findById(anyLong())).thenReturn(Optional.of(testBorrow));

        Borrow foundBorrow = null;
        try {
            foundBorrow = borrowService.getBorrowById(1L);
        } catch (BorrowNotFoundException e) {
            fail("BorrowNotFoundException expected");
        }

        assertNotNull(foundBorrow);
        assertEquals(testBorrow.getId(), foundBorrow.getId());
        assertEquals(testBorrow.getBook().getTitle(), foundBorrow.getBook().getTitle());
        assertEquals(testBorrow.getUser().getUserName(), foundBorrow.getUser().getUserName());
    }

    @Test
    public void testGetBorrowByIdNotFound() {
        when(borrowRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BorrowNotFoundException.class, () -> {
            borrowService.getBorrowById(1L);
        });
    }

    @Test
    public void testGetAllBorrows() {
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(testBorrow);

        when(borrowRepository.findAll()).thenReturn(borrowList);

        List<Borrow> foundBorrows = borrowService.getAllBorrows();

        assertNotNull(foundBorrows);
        assertFalse(foundBorrows.isEmpty());
        assertEquals(1, foundBorrows.size());
        assertEquals(testBorrow.getId(), foundBorrows.get(0).getId());
        assertEquals(testBorrow.getBook().getTitle(), foundBorrows.get(0).getBook().getTitle());
        assertEquals(testBorrow.getUser().getUserName(), foundBorrows.get(0).getUser().getUserName());
    }

    @Test
    public void testDeleteBorrowById() {
        when(borrowRepository.findById(anyLong())).thenReturn(Optional.of(testBorrow));

        Boolean deleted = null;
        try {
            deleted = borrowService.deleteBorrowById(1L);
        } catch (BorrowNotFoundException e) {
            fail("Unexpected BorrowNotFoundException");
        }

        assertTrue(deleted);
    }

    @Test
    public void testDeleteBorrowByIdNotFound() {
        when(borrowRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BorrowNotFoundException.class, () -> {
            borrowService.deleteBorrowById(1L);
        });
    }
}
