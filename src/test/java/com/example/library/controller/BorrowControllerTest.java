package com.example.library.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.library.controller.BorrowController;
import com.example.library.entity.Book;
import com.example.library.entity.Borrow;
import com.example.library.entity.User;
import com.example.library.exception.BorrowNotFoundException;
import com.example.library.service.BorrowService;

@ExtendWith(MockitoExtension.class)
public class BorrowControllerTest {

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private BorrowController borrowController;

    private Borrow testBorrow;

    @BeforeEach
    public void setUp() {
        // Sample Borrow object for testing
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");

        User user = new User();
        user.setUserName("sampleuser");

        testBorrow = new Borrow(1L, book, user, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    public void testSaveBorrow() {
        when(borrowService.createborrow(any(Borrow.class))).thenReturn(testBorrow);

        ResponseEntity<?> response = borrowController.saveBorrow(testBorrow);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testBorrow, response.getBody());
    }

    @Test
    public void testGetBorrowById() {
        when(borrowService.getBorrowById(anyLong())).thenReturn(testBorrow);

        ResponseEntity<?> response = borrowController.getBorrowById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBorrow, response.getBody());
    }

    @Test
    public void testGetBorrowByIdNotFound() {
        when(borrowService.getBorrowById(anyLong())).thenThrow(new BorrowNotFoundException("Borrow not found"));

        ResponseEntity<?> response = borrowController.getBorrowById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No Borrow exists with this BorrowId : 1", response.getBody());
    }

    @Test
    public void testGetBorrows() {
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(testBorrow);

        when(borrowService.getAllBorrows()).thenReturn(borrowList);

        ResponseEntity<?> response = borrowController.getBorrows();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(borrowList, response.getBody());
    }

    @Test
    public void testGetBorrowsEmpty() {
        when(borrowService.getAllBorrows()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = borrowController.getBorrows();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
        assertEquals("No existing Borrows in the database", response.getBody());
    }

    @Test
    public void testUpdateBorrowRecord() {
        when(borrowService.getBorrowById(anyLong())).thenReturn(testBorrow);
        when(borrowService.createborrow(any(Borrow.class))).thenReturn(testBorrow);

        ResponseEntity<Borrow> response = borrowController.updateBorrowRecord(1L, testBorrow);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBorrow, response.getBody());
    }

    @Test
    public void testUpdateBorrowRecordNotFound() {
        when(borrowService.getBorrowById(anyLong())).thenReturn(null);

        ResponseEntity<Borrow> response = borrowController.updateBorrowRecord(1L, testBorrow);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteBorrowById() {
        when(borrowService.deleteBorrowById(anyLong())).thenReturn(true);

        ResponseEntity<?> response = borrowController.deleteBorrowById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Borrow deleted Successfully", response.getBody());
    }

    @Test
    public void testDeleteBorrowByIdNotFound() {
        when(borrowService.deleteBorrowById(anyLong())).thenThrow(new BorrowNotFoundException("Borrow not found"));

        ResponseEntity<?> response = borrowController.deleteBorrowById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to delete borrow: Borrow not found", response.getBody());
    }
}
