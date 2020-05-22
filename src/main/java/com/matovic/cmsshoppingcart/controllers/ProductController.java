package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.ProductRepository;
import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Product product, Model model) {

        // saljemo kategorije jer nam treba category_id jer se bira kategorija prilikom dodavanja
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/add";
    }
}
