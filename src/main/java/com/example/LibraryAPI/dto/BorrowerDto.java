package com.example.LibraryAPI.dto;

import com.example.LibraryAPI.model.Loan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class BorrowerDto {
    @NotBlank
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private List<Loan> loans = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
