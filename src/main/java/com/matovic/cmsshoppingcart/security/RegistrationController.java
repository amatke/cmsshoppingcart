package com.matovic.cmsshoppingcart.security;

import com.matovic.cmsshoppingcart.models.UserRepository;
import com.matovic.cmsshoppingcart.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    public String register(@ModelAttribute User user){
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }

        if(!user.getConfirmPassword().equals(user.getPassword()) || user.getConfirmPassword().isEmpty()){
            model.addAttribute("notMatchingPasswords", "Passwords do not match!");
            return "register";
        }

        System.out.println("User registered " + user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login";                 // ili return "redirect:/login";
    }
}
