package com.example.cloudstorage.controllers;

import com.example.cloudstorage.dtos.UserDto;
import com.example.cloudstorage.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {

        model.addAttribute("registrationForm", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("registrationForm") @Valid UserDto userDto,
                               BindingResult bindingResult,
                               Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            model.addAttribute("passIsNotConfirm", "Пароли не совпадают!");
            return "registration";
        }
        if (!userService.createUser(userDto)) {
            model.addAttribute("userExists", "Пользовател уже существует!");
            return "registration";
        }

        return "redirect:/";
    }

}
