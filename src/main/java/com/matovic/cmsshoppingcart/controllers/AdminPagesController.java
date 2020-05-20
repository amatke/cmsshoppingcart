package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.PageRepository;
import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private PageRepository pageRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        return "admin/pages/index";
    }


    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {
        //model.addAttribute("page", new Page());       // legacy nacin - bolje je preko anotacije @ModelAttribute
        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            return "admin/pages/add";   // vracamo samo gresku
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");    // message for bootstrap

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
                                                : page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepository.findBySlug(slug);
        if(slugExists != null){
            redirectAttributes.addFlashAttribute("message", "Slug exists, chose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
        } else {
            page.setSlug(slug);
            page.setSorting(100);   // svaka strana koja se doda postaje zadnja

            pageRepository.save(page);      // cuvamo page
        }

        return "redirect:/admin/pages/add";
    }
}
