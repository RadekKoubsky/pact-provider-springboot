package org.rkoubsky.pact.example.springboot.provider;

public class NoEntityException extends RuntimeException {

    public NoEntityException(String message) {
        super(message);
    }
}
