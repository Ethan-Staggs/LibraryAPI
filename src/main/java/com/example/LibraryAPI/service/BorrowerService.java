package com.example.LibraryAPI.service;

import com.example.LibraryAPI.dto.BookDto;
import com.example.LibraryAPI.dto.BorrowerDto;
import com.example.LibraryAPI.model.Author;
import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.model.Borrower;
import com.example.LibraryAPI.model.Loan;
import com.example.LibraryAPI.repository.BookRepository;
import com.example.LibraryAPI.repository.BorrowerRepository;
import com.example.LibraryAPI.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;

    private final LoanRepository loanRepository;

    public BorrowerService(BorrowerRepository borrowerRepository, BookRepository bookRepository, LoanRepository loanRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower getBorrowerById(int id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
    }

    public Borrower addBorrower(Borrower borrower) {

        Borrower newBorrower = new Borrower();
        newBorrower.setName(borrower.getName());
        newBorrower.setEmail(borrower.getEmail());
        newBorrower.setLoans(new ArrayList<>());

        return borrowerRepository.save(newBorrower);
    }

    public Borrower updateBorrower(Borrower borrower) {
        Borrower updatedBorrower = getBorrowerById(borrower.getId()); 

        updatedBorrower.setName(borrower.getName());
        updatedBorrower.setEmail(borrower.getEmail());

        return borrowerRepository.save(updatedBorrower);
    }

    public void deleteBorrower(int id) {
        if (!borrowerRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        borrowerRepository.deleteById(id);
    }

    public void borrowBook(int borrowerId, int bookId) {
        Borrower borrower = borrowerRepository.getReferenceById(borrowerId);
        Loan loan = new Loan();
        Book loanedBook = bookRepository.getReferenceById(bookId);
        loan.setBorrower(borrower);
        loan.setBook(loanedBook);
        loan.setDueDate(LocalDate.now().plusDays(15));
        loan.setReturned(false);
        borrower.addLoan(loan);

        loanRepository.save(loan);
    }

    public void returnBook(int borrowerId, int bookId, int loanId) {
        Borrower borrower = borrowerRepository.getById(borrowerId);
        Loan loan = loanRepository.getReferenceById(loanId);
        Book book = bookRepository.getReferenceById(bookId);


        borrower.removeLoan(loan);
        loan.setReturned(true);


    }
}
