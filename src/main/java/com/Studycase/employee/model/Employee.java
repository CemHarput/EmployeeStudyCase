package com.Studycase.employee.model;


import com.Studycase.employee.enums.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Employee extends BaseEntity {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate joinDate;
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Department department;

    public Employee(String firstName, String lastName, LocalDate dateOfBirth, LocalDate joinDate, BigDecimal salary, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.joinDate = joinDate;
        this.salary = salary;
        this.department = department;
    }

    public Employee() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(getFirstName(), employee.getFirstName()) && Objects.equals(getLastName(), employee.getLastName()) && Objects.equals(getDateOfBirth(), employee.getDateOfBirth()) && Objects.equals(getJoinDate(), employee.getJoinDate()) && Objects.equals(getSalary(), employee.getSalary()) && getDepartment() == employee.getDepartment();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getDateOfBirth(), getJoinDate(), getSalary(), getDepartment());
    }
}
