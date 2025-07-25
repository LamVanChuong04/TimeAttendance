package com.TimeAttendance.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Model.Department;
import com.TimeAttendance.Model.Division;
import com.TimeAttendance.Model.Employee;
import com.TimeAttendance.Model.Job;
import com.TimeAttendance.Model.Position;
import com.TimeAttendance.Payload.Request.EmployeeRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Repository.DepartmentRepository;
import com.TimeAttendance.Repository.DivisionRepository;
import com.TimeAttendance.Repository.EmployeeRepository;
import com.TimeAttendance.Repository.JobRepository;
import com.TimeAttendance.Repository.PositionRepository;
import com.TimeAttendance.Service.UserDetailsImpl;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController

public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DivisionRepository divisionRepository;
    private final JobRepository jobRepository;
    private final PositionRepository positionRepository;
    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                              DivisionRepository divisionRepository, JobRepository jobRepository,
                              PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
        this.jobRepository = jobRepository;
        this.positionRepository = positionRepository;
    }
    
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployee() {
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
    // thêm nhân viên
    @PostMapping("/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request) {
        Optional<Employee> existingEmployee = employeeRepository.findByFullName(request.getFullName());
        Optional<Department> departmentOpt = departmentRepository.findById(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionRepository.findById(request.getDivisionId());
        Optional<Job> jobOpt = jobRepository.findById(request.getJobId());
        Optional<Position> positionOpt = positionRepository.findById(request.getPositionId());

        if (departmentOpt.isEmpty() || divisionOpt.isEmpty() || jobOpt.isEmpty() || positionOpt.isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body("Phòng ban, bộ phận, công việc hoặc chức vụ không tồn tại.");
        }
        if( existingEmployee.isPresent()) {
            return ResponseEntity
                .ok()
                .body("Nhân viên với tên " + request.getFullName() + " đã tồn tại.");
        }
        Employee employee = new Employee();
        
        employee.setFullName(request.getFullName());

        employee.setImage(request.getImage());
        employee.setAddress(request.getAddress());
        employee.setCccd(request.getCccd());
        employee.setGender(request.getGender());
        employee.setPhone(request.getPhone());
        employee.setDepartment(departmentOpt.get());
        employee.setDivision(divisionOpt.get());
        employee.setJob(jobOpt.get());
        employee.setPosition(positionOpt.get());
        employee.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        
        employeeRepository.save(employee);

        return ResponseEntity.ok("Thêm nhân viên thành công.");
    }
    // nhân viên update thông tin của mình
    @PutMapping("/employee/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Employee> employeeOptional = employeeRepository.findById(userDetails.getId());

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Vui lòng đăng nhập để cập nhật thông tin."));
        }
        
        Optional<Department> departmentOpt = departmentRepository.findById(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionRepository.findById(request.getDivisionId());
        Optional<Job> jobOpt = jobRepository.findById(request.getJobId());
        Optional<Position> positionOpt = positionRepository.findById(request.getPositionId());
        
        Employee employee = employeeOptional.get();
        employee.setFullName(request.getFullName());
        employee.setImage(request.getImage());
        employee.setAddress(request.getAddress());
        employee.setCccd(request.getCccd());
        employee.setGender(request.getGender());
        employee.setPhone(request.getPhone());
        employee.setDepartment(departmentOpt.get());
        employee.setDivision(divisionOpt.get());
        employee.setJob(jobOpt.get());
        employee.setPosition(positionOpt.get());
        employee.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        
        employeeRepository.save(employee);
        return ResponseEntity.ok("Cập nhật thông tin thành công.");
    }
    // admin update thông tin của nhân viên
    @PutMapping("/employee/update/{id}")
    public ResponseEntity<?> updateEmployeeByAdmin(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Employee> employeeOptional = employeeRepository.findById(userDetails.getId());
        
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        }
        
        Optional<Department> departmentOpt = departmentRepository.findById(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionRepository.findById(request.getDivisionId());
        Optional<Job> jobOpt = jobRepository.findById(request.getJobId());
        Optional<Position> positionOpt = positionRepository.findById(request.getPositionId());
        if (request.getFullName()== null||departmentOpt.isEmpty() || divisionOpt.isEmpty() || jobOpt.isEmpty() || positionOpt.isEmpty()) {
            return ResponseEntity
                .ok()
                .body("Vui lòng nhập dầy đủ thông tin.");
        }
        Employee employee = employeeOptional.get();
        employee.setFullName(request.getFullName());
        employee.setImage(request.getImage());
        employee.setAddress(request.getAddress());
        employee.setCccd(request.getCccd());
        employee.setGender(request.getGender());
        employee.setPhone(request.getPhone());
        employee.setDepartment(departmentOpt.get());
        employee.setDivision(divisionOpt.get());
        employee.setJob(jobOpt.get());
        employee.setPosition(positionOpt.get());
        employee.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        employeeRepository.save(employee);
        // Trả về phản hồi thành công
        return ResponseEntity.ok("Cập nhật nhân viên thành công.");
    } 
    // xóa nhân viên
    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employ = employeeRepository.findById(id);
        if(employ.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        } else {
            employeeRepository.deleteById(id);;
            return ResponseEntity.ok("Xóa nhân viên thành công.");
        }
    }
    

}
