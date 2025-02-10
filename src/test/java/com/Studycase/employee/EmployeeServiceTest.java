package com.Studycase.employee;


import com.Studycase.employee.dto.CreateEmployeeDTO;
import com.Studycase.employee.dto.EmployeeDTO;
import com.Studycase.employee.enums.Department;
import com.Studycase.employee.exception.EmployeeCreationException;
import com.Studycase.employee.model.Employee;
import com.Studycase.employee.repository.EmployeeRepository;
import com.Studycase.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private CreateEmployeeDTO createEmployeeDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setSalary(BigDecimal.valueOf(50000));
        employee.setDepartment(Department.IT);
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setJoinDate(LocalDate.of(2020, 1, 1));

        createEmployeeDTO = new CreateEmployeeDTO(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                BigDecimal.valueOf(50000),
                LocalDate.of(2020, 1, 1),
                "IT"
        );
    }

    @Test
    void shouldCreateEmployeeSuccessfully() {
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId(1L);
            return savedEmployee;
        });

        Long employeeId = employeeService.createEmployee(createEmployeeDTO);
        assertNotNull(employeeId);
        assertEquals(1L, employeeId);

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsZero() {
        CreateEmployeeDTO invalidDto = new CreateEmployeeDTO("Jane", "Doe", LocalDate.of(1995, 5, 5),BigDecimal.ZERO,LocalDate.of(2022, 3, 3), "IT");
        assertThrows(EmployeeCreationException.class, () -> employeeService.createEmployee(invalidDto));
    }

    @Test
    void shouldFindEmployeeByIdSuccessfully() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findEmployeeById(1L);
        assertNotNull(foundEmployee);
        assertEquals("John", foundEmployee.getFirstName());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.findEmployeeById(1L));
    }

    @Test
    void shouldReturnEmployeesBasedOnCriteria() {
        when(employeeRepository.findAll(any(Specification.class))).thenReturn(List.of(employee));

        List<EmployeeDTO> employees = employeeService.getEmployees("John", BigDecimal.valueOf(40000), BigDecimal.valueOf(60000));
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());

        verify(employeeRepository, times(1)).findAll(any(Specification.class));
    }
}
