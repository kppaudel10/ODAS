package com.oda.controller.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping("/home")
    public String getRegistrationPage(){
        return "registrationpage/registration";
    }
}
