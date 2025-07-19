package com.TimeAttendance.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Model.Employee;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @GetMapping("/employees")
    public ResponseEntity getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    Optional<Employee> employeeOptional = employeeRepository.findById(id);
    if (employeeOptional.isPresent()) {
        return ResponseEntity.ok(employeeOptional.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        }
    }
    
}
