package com.TimeAttendance.Service.Impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.TimeAttendance.Service.AuthService;
import com.TimeAttendance.utils.Const;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImp implements AuthService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeServiceImp employeeServiceImp;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public MessageResponse signupUser(SignupRequest signUpRequest) throws MessagingException {
        // Check username exists
        if (employeeServiceImp.checkUsername(signUpRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        // Create Employee
        Employee employee = new Employee();
        employee.setUsername(signUpRequest.getUsername());
        employee.setEmail(signUpRequest.getEmail());
        employee.setPassword(encoder.encode(signUpRequest.getPassword()));
        employee.setFullName(signUpRequest.getFullName());

        // Assign roles
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
        employee.setRoles(roles);

        // Save employee
        employeeRepository.save(employee);

        // Send email
        DataMailDto dataMail = new DataMailDto();
        dataMail.setTo(signUpRequest.getEmail());
        dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

        Map<String, Object> props = new HashMap<>();
        props.put("fullname", signUpRequest.getFullName());
        props.put("username", signUpRequest.getUsername());
        dataMail.setProps(props);

        mailServiceImpl.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);

        return new MessageResponse("User registered successfully!");
    }
    @Override
    public UserInfoResponse signinUser(LoginRequest loginRequest) {
        // Xác thực username & password
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        // Lưu authentication vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lấy thông tin user
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Tạo JWT cookie
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        // Lấy roles
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        // Trả thông tin user
        return new UserInfoResponse(
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles // có thể thêm field cookie vào DTO nếu muốn
        );
    }
}
