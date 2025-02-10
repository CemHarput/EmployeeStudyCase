package com.Studycase.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Cannot create employee")
public class EmployeeCreationException extends RuntimeException{
    public EmployeeCreationException(String message) {
        super(message);
    }
}
