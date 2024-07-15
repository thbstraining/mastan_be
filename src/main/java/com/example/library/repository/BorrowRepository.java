package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

}
