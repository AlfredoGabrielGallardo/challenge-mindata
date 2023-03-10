package com.mindata.challenge.model.exception;

import jakarta.validation.ValidationException;
public class InvalidHeroIdException extends ValidationException {
    public InvalidHeroIdException(String message) {
        super(message);
    }
}
