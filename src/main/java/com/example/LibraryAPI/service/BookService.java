package com.example.LibraryAPI.service;

import com.example.LibraryAPI.ApiService;
import com.example.LibraryAPI.model.Author;
import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.repository.AuthorRepository;
import com.example.LibraryAPI.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ApiService apiService;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ApiService apiService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.apiService = apiService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<String> getBookByTitle(String title) {
        return apiService.getBookByTitle(title);
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public Book addBook(Book book) {
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setTotalCopies(book.getTotalCopies());
        newBook.setAvailableCopies(book.getAvailableCopies());
        newBook.setAuthor(author);

        return bookRepository.save(newBook);
    }

    public Book updateBook(Book book) {
        Book updatedBook = getBookById(book.getId());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTotalCopies(book.getTotalCopies());
        updatedBook.setAvailableCopies(book.getAvailableCopies());

        return bookRepository.save(updatedBook);
    }

    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
    }

}
