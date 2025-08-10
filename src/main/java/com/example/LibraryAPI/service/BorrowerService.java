package com.example.LibraryAPI.service;

import com.example.LibraryAPI.dto.BookDto;
import com.example.LibraryAPI.dto.BorrowerDto;
import com.example.LibraryAPI.model.Author;
import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.model.Borrower;
import com.example.LibraryAPI.model.Loan;
import com.example.LibraryAPI.repository.BorrowerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower getBorrowerById(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
    }

    public Borrower addBorrower(BorrowerDto dto) {

        Borrower borrower = new Borrower();
        borrower.setName(dto.getName());
        borrower.setEmail(dto.getEmail());
        borrower.setLoans(new ArrayList<>());

        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(BorrowerDto borrower) {
        Borrower updatedBorrower = getBorrowerById(borrower.getId());

        updatedBorrower.setName(borrower.getName());
        updatedBorrower.setEmail(borrower.getEmail());

        return borrowerRepository.save(updatedBorrower);
    }

    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        borrowerRepository.deleteById(id);
    }
}
