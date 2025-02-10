package com.Studycase.employee.dto;

import com.Studycase.employee.enums.Department;
import com.Studycase.employee.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDTO(String firstName, String lastName, LocalDate dateOfBirth, BigDecimal salary,LocalDate joinDate, String department) {
    public static EmployeeDTO convertFromEmployee(Employee employee) {
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getDateOfBirth(), employee.getSalary(),employee.getJoinDate(), employee.getDepartment().toString());
    }
}
