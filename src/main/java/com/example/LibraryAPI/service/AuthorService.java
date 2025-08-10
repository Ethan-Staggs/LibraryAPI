package com.example.LibraryAPI.service;

import com.example.LibraryAPI.dto.AuthorDto;
import com.example.LibraryAPI.model.Author;
import com.example.LibraryAPI.repository.AuthorRepository;
import com.example.LibraryAPI.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author getAuthorById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(AuthorDto dto) {
        Author author = new Author();

        author.setName(dto.getName());

        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        Author a = getAuthorById(author.getId());
        a.setName(author.getName());

        return authorRepository.save(a);
    }

    public void deleteAuthor(long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author not found");
        }

        authorRepository.deleteById(id);
    }
}
