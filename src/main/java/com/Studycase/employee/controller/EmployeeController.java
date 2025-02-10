package com.Studycase.employee.controller;

import com.Studycase.employee.dto.CreateEmployeeDTO;
import com.Studycase.employee.dto.EmployeeDTO;
import com.Studycase.employee.exception.EmployeeCreationException;
import com.Studycase.employee.model.ApiResponse;
import com.Studycase.employee.model.Employee;
import com.Studycase.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    private static final Log logger = LogFactory.getLog(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal fromSalary,
            @RequestParam(required = false) BigDecimal toSalary) {

        logger.debug("getAllEmployees is started with filters - name: "+name+"fromSalary: "+fromSalary+" toSalary: "+toSalary);

        List<EmployeeDTO> employeeDtos = employeeService.getEmployees(name, fromSalary, toSalary);

        return ResponseEntity.ok(employeeDtos);
    }


    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody CreateEmployeeDTO createEmployeeDTO) {
        try {
            logger.debug("createEmployee is started");
            Long employeeId = employeeService.createEmployee(createEmployeeDTO);
            logger.info("Employee created successfully with ID: "+employeeId);
            return new ResponseEntity<>(employeeId.toString(), HttpStatus.CREATED);
        } catch (EmployeeCreationException e) {
            logger.error("createEmployee failed due to: "+ e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error during employee creation: {}"+ e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<?>> getEmployeeById(@PathVariable Long employeeId) {
        try {
            logger.debug("updateEmployee is started for employeeId: "+ employeeId);
            Employee employee = employeeService.findEmployeeById(employeeId);

            ApiResponse<Employee> response = new ApiResponse<>();
            response.setData(employee);
            response.setMessage("Employee get successfully");
            response.setStatus(HttpStatus.OK.value());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating employee: " + e.getMessage());

            ApiResponse<String> response = new ApiResponse<>();
            response.setData(null);
            response.setMessage("Internal server error");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
