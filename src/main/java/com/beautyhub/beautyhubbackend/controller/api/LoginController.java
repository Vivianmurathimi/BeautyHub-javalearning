package com.beautyhub.beautyhubbackend.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/countries";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}