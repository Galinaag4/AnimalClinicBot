package com.example.animalclinicbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatException extends RuntimeException{

    public CatException() {
    }

    public CatException(String message) {
        super("Кот не найден");
    }
}
