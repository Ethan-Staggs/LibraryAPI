package com.example.LibraryAPI.dto;

import com.example.LibraryAPI.model.Book;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class AuthorDto {

    @NotBlank(message = "Name is required")
    private String name;

    private List<String> bookTitles;

    public AuthorDto() {
    }

    public AuthorDto(String name, List<String> bookTitles) {
        this.name = name;
        this.bookTitles = bookTitles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBookTitles() {
        return bookTitles;
    }

    public void setBookTitles(List<String> bookTitles) {
        this.bookTitles = bookTitles;
    }
}
