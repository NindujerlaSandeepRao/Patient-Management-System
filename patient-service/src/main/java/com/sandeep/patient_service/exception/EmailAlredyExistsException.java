package com.sandeep.patient_service.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailAlredyExistsException extends RuntimeException {
    public EmailAlredyExistsException(String message) {
        super(message);
    }
}
