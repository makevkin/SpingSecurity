package ru.kata.spring.boot_security.demo.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
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
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("newUser", new User());
        return "indexSpringMvc";
    }

    @GetMapping("/addUser")
    public String addUserView(Model model, Principal principal) {
        User user = new User();
        String username = principal.getName();
        model.addAttribute("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("username", username);
        model.addAttribute("allRoles", roles);
        return "addUserField";
    }

    @PatchMapping("/addOrUpdate/{id}")
    public String add(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = (List<Role>) roleRepository.findAll();
            model.addAttribute("allRoles", roles);
            return "addUserField";
        } else {
            userService.update(id, user);
            return "redirect:/admin";
        }

    }

    @PostMapping("/addOrUpdate")
    public String add(@Valid @ModelAttribute("user") User user, Model model) {
        userService.save(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/removeUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute("id") Long id, Model model) {
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "editPage";
    }



}
