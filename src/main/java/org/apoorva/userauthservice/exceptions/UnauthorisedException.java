package org.apoorva.userauthservice.exceptions;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message) {
        super(message);
    }
}
