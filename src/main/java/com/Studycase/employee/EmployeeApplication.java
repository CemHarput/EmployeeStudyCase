package com.Studycase.employee;

import com.Studycase.employee.enums.Department;
import com.Studycase.employee.model.Employee;
import com.Studycase.employee.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner {
	private final EmployeeRepository employeeRepository;

    public EmployeeApplication(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Employee jimEmployee = new Employee(
				"Jim",
				"Halpert",
				LocalDate.of(1997, 5, 10),
				LocalDate.of(2023, 5, 15),
				new BigDecimal("20000"),
				Department.IT);

		Employee dwightEmployee = new Employee(
				"Dwight",
				"Schrute",
				LocalDate.of(1985, 1, 20),
				LocalDate.of(2015, 6, 10),
				new BigDecimal("25000"),
				Department.BUSINESS);

		Employee michaelEmployee = new Employee(
				"Michael",
				"Scott",
				LocalDate.of(1975, 3, 15),
				LocalDate.of(2005, 7, 5),
				new BigDecimal("30000"),
				Department.IT);

		Employee stanleyEmployee = new Employee(
				"Stanley",
				"Hudson",
				LocalDate.of(1968, 2, 19),
				LocalDate.of(2007, 4, 12),
				new BigDecimal("22000"),
				Department.BUSINESS);

		Employee mohamedEmployee = new Employee(
				"Mohamed",
				"Ahmed",
				LocalDate.of(2000, 1, 1),
				LocalDate.of(2023, 5, 18),
				new BigDecimal("1000"),
				Department.IT);
		Employee salmanEmployee = new Employee(
				"Salman",
				"Ebrahim",
				LocalDate.of(1990, 1, 1),
				LocalDate.of(2016, 3, 18),
				new BigDecimal("2000"),
				Department.BUSINESS);

		employeeRepository.saveAll(Arrays.asList(jimEmployee,dwightEmployee,michaelEmployee,stanleyEmployee,mohamedEmployee,salmanEmployee));
	}

}
