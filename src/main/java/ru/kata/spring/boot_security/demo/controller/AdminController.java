package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class AdminController {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(RoleRepository roleRepository, UserService userService, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String indexView(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("users", userService.findAll());
        model.addAttribute("username", principal.getName());
        model.addAttribute("user", userRepository.findByUserName(principal.getName()).get());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("newUser", new User());
        return "indexSpringMvc";
    }



}
