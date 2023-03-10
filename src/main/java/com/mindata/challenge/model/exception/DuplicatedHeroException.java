package com.mindata.challenge.model.exception;

public class DuplicatedHeroException extends RuntimeException {
    public DuplicatedHeroException(String message) {
        super(message);
    }
}
