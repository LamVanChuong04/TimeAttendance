package com.TimeAttendance.Payload.Request;

import java.util.Set;

public class SignupRequest {
    
    private String username;
    private String email;
    private Set<String> role;
    private String fullName;

    private String password;
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

 
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public SignupRequest(String username, String email, Set<String> role, String fullName, String password) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.password = password;
    }

    public SignupRequest(){
        
    }
    
}

