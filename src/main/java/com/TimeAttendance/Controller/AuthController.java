package com.TimeAttendance.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Jwt.JwtUtils;
import com.TimeAttendance.Model.ERole;
import com.TimeAttendance.Model.Role;
import com.TimeAttendance.Model.User;
import com.TimeAttendance.Payload.Request.LoginRequest;
import com.TimeAttendance.Payload.Request.SignupRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Payload.Respone.UserInfoResponse;
import com.TimeAttendance.Repository.RoleRepository;
import com.TimeAttendance.Repository.UserRepository;
import com.TimeAttendance.Service.UserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
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
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    // Create new user's account
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setFullName(signUpRequest.getFullName());

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

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

  
}
  

