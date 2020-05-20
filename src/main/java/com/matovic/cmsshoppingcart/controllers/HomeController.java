package com.matovic.cmsshoppingcart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";          // vraca home.html fajl iz foldera templates
    }
}
