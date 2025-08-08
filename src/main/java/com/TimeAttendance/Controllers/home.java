package com.TimeAttendance.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class home {
    @GetMapping("/")
    public String homePage() {
        return "TRANG HOME";
    }
    
}
