package com.example.LibraryAPI.service;

import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.model.Borrower;
import com.example.LibraryAPI.model.Loan;
import com.example.LibraryAPI.repository.BookRepository;
import com.example.LibraryAPI.repository.BorrowerRepository;
import com.example.LibraryAPI.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @PutMapping("/updateBorrower/{id}")
    public Borrower updateBorrower(Borrower borrower, int id) {
        Borrower updatedBorrower = getBorrowerById(id);

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
        loan.setLoanDate(LocalDate.now());
        loan.setReturned(false);
        borrower.addLoan(loan);

        loanRepository.save(loan);
    }

    //
    public void returnBook(int borrowerId, int loanId, int bookId) {
        Borrower borrower = borrowerRepository.getReferenceById(borrowerId);
        Loan loan = loanRepository.getReferenceById(loanId);
        System.out.println(loan.getId());
        Book book = bookRepository.getReferenceById(bookId);


        borrower.removeLoan(loan);
        loanRepository.delete(loan);
        loan.setReturned(true);


    }
}
