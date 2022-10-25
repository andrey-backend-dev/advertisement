package com.senla.advertisement.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>("Authentication issues. Incorrect login or password. Try again.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>("Authentication issues. Log in / Register, firstly.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> expiredJwtException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Your JWT Token is expired. Try to log in again.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Access denied. You do not have enough rights to visit this page.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> signatureException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Signature issues. Incorrect JWT. Try again.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> malformedJwtException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Malformed issues. Incorrect format of the JWT.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> uniqueConstraintException(RuntimeException ex, WebRequest request) {
        String field = ex.getMessage().split("\\[")[2].split("]")[0].split("\\.")[1].split("_")[0];
        return new ResponseEntity<Object>("The " + field + ", you've entered, is taken. Please, write another value.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity<Object>(ex.getMessage(), status);
//    }
}
