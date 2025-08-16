package com.example.LibraryAPI.controller;

import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.model.Borrower;
import com.example.LibraryAPI.service.BorrowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/borrower")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/allBorrowers")
    public List<Borrower> allBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/byId/{id}")
    public Borrower getBorrowerById(@PathVariable int id) {
        return borrowerService.getBorrowerById(id);
    }

    @PostMapping("/addBorrower")
    public ResponseEntity<Borrower> addBorrower(@RequestBody Borrower borrower) {
        Borrower newBorrower = borrowerService.addBorrower(borrower);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBorrower);
    }

    @PutMapping("/updateBorrower/{id}")
    public ResponseEntity<String> updateBorrower(@RequestBody Borrower borrower, @PathVariable int id) {

        borrowerService.updateBorrower(borrower, id);

        return ResponseEntity.ok("Borrower successfully updated");
    }

    @DeleteMapping("deleteBorrower/{id}")
    public void deleteBorrower(@PathVariable int id) {
        borrowerService.deleteBorrower(id);
    }

    @PostMapping("/borrow/{borrowerId}/book/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable int borrowerId, @PathVariable int bookId) {
        borrowerService.borrowBook(borrowerId, bookId);

        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("return/borrower/{borrowerId}/loan/{loanId}/book/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable int borrowerId, @PathVariable int loanId, @PathVariable int bookId) {
        borrowerService.returnBook(borrowerId, loanId, bookId);

        return ResponseEntity.ok("Book returned successfully");
    }
}
