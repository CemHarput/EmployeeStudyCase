package com.Studycase.employee.service;


import com.Studycase.employee.dto.CreateEmployeeDTO;
import com.Studycase.employee.dto.EmployeeDTO;
import com.Studycase.employee.enums.Department;
import com.Studycase.employee.exception.EmployeeCreationException;
import com.Studycase.employee.model.Employee;
import com.Studycase.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> getEmployees(String name, BigDecimal fromSalary, BigDecimal toSalary) {
        List<Employee> employees = employeeRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                Predicate namePredicate = cb.or(
                        cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
                );
                predicates.add(namePredicate);
            }

            if (fromSalary != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), fromSalary));
            }

            if (toSalary != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("salary"), toSalary));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return employees.stream()
                .map(EmployeeDTO::convertFromEmployee)
                .collect(Collectors.toList());
    }




    public Long createEmployee(CreateEmployeeDTO createEmployeeDTO) {

        if (Objects.isNull(createEmployeeDTO.salary()) || createEmployeeDTO.salary().compareTo(BigDecimal.ZERO) == 0) {
            throw new EmployeeCreationException("Salary cannot be null or zero");
        }

        Employee employee = new Employee();
        mapDtoToEmployee(createEmployeeDTO,employee);
        employeeRepository.save(employee);
        return employee.getId();
    }
    public Employee findEmployeeById(Long employeeId)  {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
    }



    private void mapDtoToEmployee(CreateEmployeeDTO dto, Employee employee) {
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setDateOfBirth(dto.dateOfBirth());
        employee.setJoinDate(dto.joinDate());
        employee.setSalary(dto.salary());
        employee.setDepartment(Department.valueOf(dto.department()));
    }
}
