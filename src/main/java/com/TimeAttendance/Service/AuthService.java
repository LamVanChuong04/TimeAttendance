package com.TimeAttendance.Service;

import com.TimeAttendance.Payload.Request.LoginRequest;
import com.TimeAttendance.Payload.Request.SignupRequest;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Payload.Respone.UserInfoResponse;

import jakarta.mail.MessagingException;

public interface AuthService {
    MessageResponse signupUser(SignupRequest signUpRequest) throws MessagingException;
    UserInfoResponse signinUser(LoginRequest loginRequest);
}
