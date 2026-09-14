package com.codepulse.codepulse.controller;

import com.codepulse.codepulse.entity.User;
import com.codepulse.codepulse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show register page
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        // Check username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Username already exists"
            );
            return "redirect:/register";
        }

        // Check email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Email already exists"
            );
            return "redirect:/register";
        }

        // Create user
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(password));

        // Save user to MySQL
        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "success",
                "Registration successful! Please login."
        );

        return "redirect:/login";
    }
}