package com.example.cloudstorage.controllers;

import com.example.cloudstorage.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
//    @Autowired
//    private UserMapper userMapper;
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model){

        model.addAttribute("user", user);
        return "login";
    }

//    @GetMapping("/profile")
//    public String profile(@AuthenticationPrincipal User user, Model model){
//
//        model.addAttribute("user", userMapper.toDto(user));
//        return "profile";
//    }

}
