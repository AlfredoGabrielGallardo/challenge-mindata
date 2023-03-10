package com.mindata.challenge.config;

import com.mindata.challenge.model.exception.DuplicatedHeroException;
import com.mindata.challenge.model.exception.HeroNotFoundException;
import com.mindata.challenge.model.exception.InvalidHeroIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdviceConfig{

    @ExceptionHandler(value = DuplicatedHeroException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatedException(DuplicatedHeroException e) {
        log.error(String.format("[DUPLICATE HERO]: %s", e.getLocalizedMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("[ERROR]", e.getMessage()));
    }

    @ExceptionHandler(value = InvalidHeroIdException.class)
    public ResponseEntity<Map<String, String>> handleInvalidHeroIdException(InvalidHeroIdException e) {
        log.error(String.format("[INVALID HERO ID]: %s", e.getLocalizedMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("[ERROR]", e.getMessage()));
    }

    @ExceptionHandler(value = HeroNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleHeroNotFoundException(HeroNotFoundException e) {
        log.error(String.format("[HERO NOT FOUND]: %s", e.getLocalizedMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("[ERROR]", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        log.error(String.format("[WRONG VALIDATION]: %s", e.getLocalizedMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("[ERROR]", "Validation failed"));
    }
}
