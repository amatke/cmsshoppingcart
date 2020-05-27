package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Category category) {
        //model.addAttribute("category", new Category());       // legacy nacin - bolje je preko anotacije @ModelAttribute
        return "admin/categories/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            return "admin/categories/add";   // vracamo samo gresku
        }

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");    // message for bootstrap

        String slug = category.getName().toLowerCase().replace(" ", "-");

        Category categoryExists = categoryRepository.findByName(category.getName());

        if(categoryExists != null){
            redirectAttributes.addFlashAttribute("message", "Slug exists, chose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("category", category);
        } else {
            category.setSlug(slug);
            category.setSorting(100);   // svaka strana koja se doda postaje zadnja

            categoryRepository.save(category);      // cuvamo category
        }

        return "redirect:/admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Category category = categoryRepository.getOne(id);
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }


    @PostMapping("/edit")
    public String edit(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.getOne(category.getId()));
            return "admin/categories/edit";   // vracamo samo gresku
        }

        redirectAttributes.addFlashAttribute("message", "Category edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");    // message for bootstrap

        String slug = category.getName().toLowerCase().replace(" ", "-");

        Category categoryExists = categoryRepository.findByName(category.getName());

        if(categoryExists != null){
            redirectAttributes.addFlashAttribute("message", "Category exists, chose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("category", category);
        } else {
            category.setSlug(slug);

            categoryRepository.save(category);      // cuvamo category
        }

        return "redirect:/admin/categories/edit/" + category.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Category deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/categories";
    }

}
