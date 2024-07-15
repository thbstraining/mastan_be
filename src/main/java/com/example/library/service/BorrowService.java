package com.example.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library.entity.Borrow;
import com.example.library.exception.BorrowNotFoundException;
import com.example.library.repository.BorrowRepository;

@Service
public class BorrowService {
	@Autowired
	private BorrowRepository borrowRepository;

	public Borrow createborrow(Borrow borrow)throws BorrowNotFoundException {
		if(borrow==null) {
			throw new BorrowNotFoundException("Borrow not found excpetion");
		}
		borrowRepository.save(borrow);
		return borrow;
	}

	public Borrow getBorrowById(long borrowId)throws BorrowNotFoundException {
		Borrow borrow=borrowRepository.findById(borrowId).orElse(null);
		if(borrow==null) {
			throw new BorrowNotFoundException("Borrow not found excpetion");
			
		}
		else {
			return borrow;
			
		}
		
	}
	public List<Borrow> getAllBorrows() {
		List<Borrow> borrows=borrowRepository.findAll();
		return borrows;
	}
	
	public Boolean deleteBorrowById(long borrowId)throws BorrowNotFoundException {
		Borrow borrow=borrowRepository.findById(borrowId).orElse(null);
		if(borrow!=null) {
			borrowRepository.delete(borrow);
			return true;
			
		}
		throw new BorrowNotFoundException("Borrow not found excpetion");
		
	}




	
	
	

}
