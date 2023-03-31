package com.example.animalclinicbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportException extends RuntimeException {
    public ReportException() {
        super("Report is not found!");
    }
}
