package com.TimeAttendance.Controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Jwt.JwtUtils;
import com.TimeAttendance.Models.ERole;
import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Models.Role;
import com.TimeAttendance.Payload.DTO.DataMailDto;
import com.TimeAttendance.Payload.Request.LoginRequest;
import com.TimeAttendance.Payload.Request.SignupRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Payload.Respone.UserInfoResponse;
import com.TimeAttendance.Repositories.EmployeeRepository;
import com.TimeAttendance.Repositories.RoleRepository;
import com.TimeAttendance.Service.MailService;
import com.TimeAttendance.Service.UserDetailsImpl;
import com.TimeAttendance.Service.Impl.MailServiceImpl;
import com.TimeAttendance.utils.Const;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;



@RestController
@RequestMapping("/auth")

@Tag(name = "User Controller")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;
    private final MailServiceImpl mailServiceImpl;
    public AuthController(AuthenticationManager authenticationManager,
        RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils,
        EmployeeRepository employeeRepository, MailServiceImpl mailServiceImpl) {
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.encoder = encoder;
      this.jwtUtils = jwtUtils;
      this.employeeRepository = employeeRepository;
      this.mailServiceImpl = mailServiceImpl;
    }
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
  public ResponseEntity<?> signupUser(@RequestBody SignupRequest signUpRequest) throws Exception {
    if (employeeRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    // Create new user's account
    Employee employ = new Employee();
    employ.setUsername(signUpRequest.getUsername());
    employ.setEmail(signUpRequest.getEmail());
    employ.setPassword(encoder.encode(signUpRequest.getPassword()));
    employ.setFullName(signUpRequest.getFullName());
    

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    employ.setRoles(roles);
    employeeRepository.save(employ);
    // send email
    try {
          DataMailDto dataMail = new DataMailDto();
          dataMail.setTo(signUpRequest.getEmail());
          dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

          Map<String, Object> props = new HashMap<>();
          props.put("fullname",signUpRequest.getFullName());
          props.put("username", signUpRequest.getUsername());
          dataMail.setProps(props);

          mailServiceImpl.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);

          
    } catch (MessagingException exp){
            exp.printStackTrace();
        }

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  @Operation(summary = "Logout", description = "Đăng xuất tài khoản")
  @PostMapping("/signout")
  public ResponseEntity<?> signoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

  
}
  

