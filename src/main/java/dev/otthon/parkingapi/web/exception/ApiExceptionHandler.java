package dev.otthon.parkingapi.web.exception;

import dev.otthon.parkingapi.exception.EntityNotFoundException;
import dev.otthon.parkingapi.exception.UsernameViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> AccessDeniedException(AccessDeniedException excecao, HttpServletRequest request) {

        log.error("API Error - ", excecao);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(
                                request,
                                HttpStatus.FORBIDDEN,
                                excecao.getMessage()
                        )
                );
    };

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> EntityNotFoundException(RuntimeException excecao, HttpServletRequest request) {

        log.error("API Error - ", excecao);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(
                                request,
                                HttpStatus.NOT_FOUND,
                                excecao.getMessage()
                        )
                );
    };

    @ExceptionHandler(UsernameViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException excecao, HttpServletRequest request) {

        log.error("API Error - ", excecao);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(
                                request,
                                HttpStatus.CONFLICT,
                                excecao.getMessage()
                        )
                );
    };

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(MethodArgumentNotValidException excecao, HttpServletRequest request, BindingResult result) {

        log.error("API Error - ", excecao);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(
                                request,
                                HttpStatus.UNPROCESSABLE_ENTITY,
                                "Campo(s) Inválido(s)",
                                result
                        )
                );
    };

}
