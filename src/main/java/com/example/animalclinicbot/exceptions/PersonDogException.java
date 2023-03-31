package com.example.animalclinicbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonDogException extends RuntimeException {
    public PersonDogException() {
        super("PersonDog is not found!");
    }
}
