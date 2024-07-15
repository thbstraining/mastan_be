package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entity.Borrow;
import com.example.library.exception.BorrowNotFoundException;
import com.example.library.service.BorrowService;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
	
	@Autowired
	private BorrowService borrowService;
	
	@PostMapping("/user/create-borrow")
	public ResponseEntity<?> saveBorrow(@RequestBody Borrow borrow){

		try {
            Borrow savedBorrow = borrowService.createborrow(borrow);
            return new ResponseEntity<>(savedBorrow, HttpStatus.CREATED);
        } catch (BorrowNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid borrow details provided");
        }
	
		
	}
	
	 @GetMapping("/user/id/{borrowId}")
	    public ResponseEntity<?> getBorrowById(@PathVariable long borrowId) {
	        try {
	            Borrow borrow = borrowService.getBorrowById(borrowId);
	            return ResponseEntity.ok(borrow);
	        } catch (BorrowNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Borrow exists with this BorrowId : " + borrowId);
	        }
	    }
	 @GetMapping("/user/all")
		public ResponseEntity<?> getBorrows() {
			List<Borrow> Borrows = borrowService.getAllBorrows();
			if (Borrows.size() > 0) {
				return new ResponseEntity<>(Borrows, HttpStatus.OK);
			}
			return new ResponseEntity<>("No existing Borrows in the database", HttpStatus.NOT_IMPLEMENTED);
		}

		@PutMapping("/{id}")
		public ResponseEntity<Borrow> updateBorrowRecord(@PathVariable("id") Long id,@RequestBody Borrow BorrowRecord) {
			Borrow existingBorrowRecord;
			try {
				existingBorrowRecord = borrowService.getBorrowById(id);
				if (existingBorrowRecord != null) {
					BorrowRecord.setId(id); // Ensure the ID is set for update
					Borrow updatedBorrowRecord = borrowService.createborrow(BorrowRecord);
					return ResponseEntity.ok(updatedBorrowRecord);
				} else {
					return ResponseEntity.notFound().build();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			return ResponseEntity.notFound().build();
		}


	    @DeleteMapping("/deleteId/{borrowId}")
	    public ResponseEntity<?> deleteBorrowById(@PathVariable long borrowId) {
	        try {
	            Boolean deletedValue = borrowService.deleteBorrowById(borrowId);
	            if (deletedValue) {
	                return ResponseEntity.ok("Borrow deleted Successfully");
	            }
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Borrow exists with id: " + borrowId);
	        } catch (BorrowNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete borrow: " + e.getMessage());
	        }
	    }
	

   
}

