package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.PageRepository;
import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private PageRepository pageRepository;

    @GetMapping
    public String home(Model model){
        model.addAttribute("page", pageRepository.findBySlug("home"));
        return "page";
    }

    @GetMapping("/{slug}")
    public String page(@PathVariable String slug, Model model){

        Page page = pageRepository.findBySlug(slug);
        if(page == null){
            return "redirect:/";
        }

        model.addAttribute("page", page);
        return "page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
