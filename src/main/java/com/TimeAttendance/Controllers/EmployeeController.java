package com.TimeAttendance.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Models.Department;
import com.TimeAttendance.Models.Division;
import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Models.Job;
import com.TimeAttendance.Models.Position;
import com.TimeAttendance.Payload.Request.EmployeeRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;

import com.TimeAttendance.Service.Impl.DepartmentServiceImp;
import com.TimeAttendance.Service.Impl.DivisionServiceImp;
import com.TimeAttendance.Service.Impl.EmployeeServiceImp;
import com.TimeAttendance.Service.Impl.JobServiceImp;
import com.TimeAttendance.Service.Impl.PositionServiceImp;
import com.TimeAttendance.Service.Impl.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@Tag(name = "Employee Controller")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImp employeeServiceImp;
    @Autowired
    private DepartmentServiceImp departmentServiceImp;
    @Autowired
    private DivisionServiceImp divisionServiceImp;
    @Autowired
    private JobServiceImp jobServiceImp;
    @Autowired
    private PositionServiceImp positionServiceImp;
    
    @Operation(summary = "Get information", description = "Lấy thông tin nhân viên")
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employees = employeeServiceImp.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get information", description = "Lấy thông tin nhân viên theo id")
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    Optional<Employee> employeeOptional = employeeServiceImp.getEmployeeById(id);
    if (employeeOptional.isPresent()) {
        return ResponseEntity.ok(employeeOptional.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        }
    }
    // thêm nhân viên
    @Operation(summary = "Add Employee", description = "Thêm nhân viên")
    @PostMapping("/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request) {
        Optional<Employee> existingEmployee = employeeServiceImp.getFullName(request.getFullName());
        Optional<Department> departmentOpt = departmentServiceImp.getDepartment(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionServiceImp.getDivision(request.getDivisionId());
        Optional<Job> jobOpt = jobServiceImp.getJob(request.getJobId());
        Optional<Position> positionOpt = positionServiceImp.getPosition(request.getPositionId());

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
        
        employeeServiceImp.Save(employee);

        return ResponseEntity.ok("Thêm nhân viên thành công.");
    }
    // nhân viên update thông tin của mình
    @Operation(summary = "Update Information", description = "Cập nhật thông tin nhân viên")
    @PutMapping("/employee/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Employee> employeeOptional = employeeServiceImp.getEmployeeById(userDetails.getId());

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Vui lòng đăng nhập để cập nhật thông tin."));
        }
        
        Optional<Department> departmentOpt = departmentServiceImp.getDepartment(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionServiceImp.getDivision(request.getDivisionId());
        Optional<Job> jobOpt = jobServiceImp.getJob(request.getJobId());
        Optional<Position> positionOpt = positionServiceImp.getPosition(request.getPositionId());
        
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
        
        employeeServiceImp.Save(employee);
        return ResponseEntity.ok("Cập nhật thông tin thành công.");
    }
    // admin update thông tin của nhân viên
    @Operation(summary = "Update information", description = "Cập nhật thông tin nhân viên theo id")
    @PutMapping("/employee/update/{id}")
    public ResponseEntity<?> updateEmployeeByAdmin(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Employee> employeeOptional = employeeServiceImp.getEmployeeById(userDetails.getId());
        
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        }
        
        Optional<Department> departmentOpt = departmentServiceImp.getDepartment(request.getDepartmentId());
        Optional<Division> divisionOpt = divisionServiceImp.getDivision(request.getDivisionId());
        Optional<Job> jobOpt = jobServiceImp.getJob(request.getJobId());
        Optional<Position> positionOpt = positionServiceImp.getPosition(request.getPositionId());
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
        employeeServiceImp.Save(employee);
        // Trả về phản hồi thành công
        return ResponseEntity.ok("Cập nhật nhân viên thành công.");
    } 
    // xóa nhân viên
    @Operation(summary = "Delete Employee", description = "Xóa nhân viên")
    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employ = employeeServiceImp.getEmployeeById(id);
        if(employ.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Không tìm thấy nhân viên với id = " + id));
        } else {
            employeeServiceImp.deleteEmployee(id);
            return ResponseEntity.ok("Xóa nhân viên thành công.");
        }
    }
    
    @Operation(summary = "Get All", description = "Lấy tất cả nhân viên")
    @GetMapping("/getAll")
    public Page<Employee> getAll(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "desc") String direction
    ){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortby = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortby);
        return employeeServiceImp.findByAllEmployee(pageable);
    }
    @Operation(summary = "Search Page", description = "Tìm kiếm theo trang")
    @GetMapping("/searchPage")
    public Page<Employee> getByUsername(@RequestParam String username,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "desc") String direction
    ){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortby = Sort.by(sortDirection, sort);
        Pageable pageable = PageRequest.of(page, size, sortby);
        return employeeServiceImp.findByUserName(username, pageable);
    }
    
    

}
