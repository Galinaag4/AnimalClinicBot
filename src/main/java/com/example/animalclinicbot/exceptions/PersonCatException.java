package com.example.animalclinicbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonCatException extends RuntimeException {

    public PersonCatException() {
    }

    public PersonCatException(String message) {
        super("Владелец не найден!");
    }
}
