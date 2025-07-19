package com.example.LibraryAPI.service;

import com.example.LibraryAPI.dto.BookDto;
import com.example.LibraryAPI.model.Author;
import com.example.LibraryAPI.model.Book;
import com.example.LibraryAPI.repository.AuthorRepository;
import com.example.LibraryAPI.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public Book addBook(BookDto dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        Book updatedBook = getBookById(book.getId());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTotalCopies(book.getTotalCopies());
        updatedBook.setAvailableCopies(book.getAvailableCopies());

        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
    }

}
