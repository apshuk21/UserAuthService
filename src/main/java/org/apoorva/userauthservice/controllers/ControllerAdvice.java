package org.apoorva.userauthservice.controllers;

import org.apoorva.userauthservice.exceptions.PasswordMismatchException;
import org.apoorva.userauthservice.exceptions.UnauthorisedException;
import org.apoorva.userauthservice.exceptions.UserAlreadyExistException;
import org.apoorva.userauthservice.exceptions.UserNotRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleUserAlreadyExistException(final UserAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUserNotRegisteredException(final UserNotRegisteredException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlePasswordMismatchException(final PasswordMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(final Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<String> handleUnauthorisedException(final UnauthorisedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }


}
