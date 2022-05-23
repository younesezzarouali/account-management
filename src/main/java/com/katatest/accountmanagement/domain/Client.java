package com.katatest.accountmanagement.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.Locale;

@Data
public class Client {
    private Long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private Boolean actif;

    public String getFullName() {
        return this.lastName.toUpperCase(Locale.ROOT) + " " + this.firstName.toUpperCase(Locale.ROOT);
    }
}
