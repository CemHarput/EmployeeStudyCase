package com.Studycase.employee.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateEmployeeDTO(String firstName, String lastName, LocalDate dateOfBirth, BigDecimal salary,LocalDate joinDate, String department) {
}
