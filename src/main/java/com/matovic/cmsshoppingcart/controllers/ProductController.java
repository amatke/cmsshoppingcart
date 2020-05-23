package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.ProductRepository;
import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Page;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model) {
        HashMap<Long, String>  cats = new HashMap<>();
        categoryRepository.findAll().forEach( cat -> cats.put(cat.getId(), cat.getName()) );
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cats", cats);
        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Product product, Model model) {

        // saljemo kategorije jer nam treba category_id jer se bira kategorija prilikom dodavanja
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/add";
    }


    @PostMapping("/add")
    public String add(@Valid Product product, BindingResult bindingResult, MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {

        if(bindingResult.hasErrors()){
            //model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            //model.addAttribute("file", file.getOriginalFilename());
            return "admin/products/add";   // vracamo samo gresku
        }

        boolean fileOk = false;
        byte[] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + fileName);

        if(fileName.endsWith("jpg") || fileName.endsWith("png")){
            fileOk = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");    // message for bootstrap


        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepository.findBySlug(slug);

        if (!fileOk){
            redirectAttributes.addFlashAttribute("message", "Image must me .jpg or .png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if(productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            product.setSlug(slug);
            product.setImage(fileName);
            productRepository.save(product);
            Files.write(path, bytes);
        }

        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product product = productRepository.getOne(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("product", product);
            return "admin/products/edit";   // vracamo samo gresku
        }

        redirectAttributes.addFlashAttribute("message", "Product edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");    // message for bootstrap

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepository.findByName(product.getName());

        if(productExists != null){
            redirectAttributes.addFlashAttribute("message", "Product exists, chose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else {
            product.setSlug(slug);

            productRepository.save(product);      // cuvamo category
        }

        return "redirect:/admin/products/edit/" + product.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/products";
    }

}