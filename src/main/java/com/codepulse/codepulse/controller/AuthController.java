package com.codepulse.codepulse.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // =========================
    // LOGIN PAGE
    // =========================
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // =========================
    // LOGOUT
    // =========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // Clear the current session
        session.invalidate();

        // Redirect to login page
        return "redirect:/login";
    }
}