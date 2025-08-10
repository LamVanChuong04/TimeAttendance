package com.TimeAttendance.Controllers;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Jwt.JwtUtils;
import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Payload.Request.LoginRequest;
import com.TimeAttendance.Payload.Request.SignupRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Payload.Respone.UserInfoResponse;

import com.TimeAttendance.Service.Impl.AuthServiceImp;
import com.TimeAttendance.Service.Impl.EmployeeServiceImp;
import com.TimeAttendance.Service.Impl.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;


@RestController
@RequestMapping("/auth")

@Tag(name = "User Controller")
public class AuthController {
    @Autowired
    private AuthServiceImp authServiceImp;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeServiceImp  employeeServiceImp;
    
    @Operation(summary = "Login", description = "Đăng nhập tài khoản")
    @PostMapping("/signin")
    public ResponseEntity<?> signinUser( @RequestBody LoginRequest loginRequest) {
    // gửi thông tin đăng nhập từ client lên server
        // Kiểm tra thông tin đăng nhập, xác thực username và password
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Nếu thông tin đăng nhập hợp lệ, lưu thông tin xác thực vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // tạo một đối tượng UserDetailsImpl từ thông tin xác thực
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // Tạo một cookie JWT từ thông tin người dùng và gửi lại cho request tiếp theo
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        // Lấy danh sách quyền của người dùng và chuyển đổi thành danh sách chuỗi
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
        // Trả về ResponseEntity với mã trạng thái OK, cookie JWT và thông tin người dùng
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                                    userDetails.getUsername(),
                                    userDetails.getEmail(),
                                    roles));
    }

    @Operation(summary = "Create account", description = "Tạo tài khoản")
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signUpRequest) throws MessagingException {
        return ResponseEntity.ok(authServiceImp.signupUser(signUpRequest));
    }

    @Operation(summary = "Logout", description = "Đăng xuất tài khoản")
    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser() {
      ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(new MessageResponse("You've been signed out!"));
    }
    @Operation(summary = "Get All", description = "Đăng nhập tài khoản")
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
    @Operation(summary = "Search Page", description = "Đăng nhập tài khoản")
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
  

