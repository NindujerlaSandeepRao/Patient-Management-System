package com.sandeep.patient_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlredyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyException(EmailAlredyExistsException ex){
        Map<String,String> errors=new HashMap<>();
        log.warn("email already exists {}",ex.getMessage());
        errors.put("message","email already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFound(PatientNotFoundException ex){
        log.warn("Patient not Found : {}",ex.getMessage());
        Map<String,String> errors=new HashMap<>();
        errors.put("message","Patient not Found");
        return ResponseEntity.badRequest().body(errors);
    }
}
