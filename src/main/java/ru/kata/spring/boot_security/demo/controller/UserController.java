package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        Optional<User> user = userRepository.findByUserName(userName);
        model.addAttribute("user", user);
        return "user";
    }
}
