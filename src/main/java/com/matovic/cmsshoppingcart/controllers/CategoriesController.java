package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.ProductRepository;
import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{slug}")
    public String category(@PathVariable String slug, Model model,
                           @RequestParam(value = "page", required = false) Integer p) throws Exception{

        int perPage = 6;                                                // proizvoda po strani
        int page = (p != null) ? p : 0;                                 // zahtevana strana
        Pageable pageable = PageRequest.of(page, perPage);

        model.addAttribute("products", productRepository.findAll(pageable));

        long count = 0;
        if (slug.equals("all")) {       // ako dodje category/all
            Page<Product> products = productRepository.findAll(pageable);
            count = products.getTotalElements();
            model.addAttribute("products", products);
            model.addAttribute("title", "All products");
        } else {
            Category category = categoryRepository.findBySlug(slug);
            if(category == null){
                return "redirect:/";
            }

            long categoryId = category.getId();
            String categoryName = category.getName();
            List<Product> products = productRepository.findAllByCategoryId(Long.toString(categoryId), pageable);
            count = productRepository.countByCategoryId(Long.toString(categoryId));

            model.addAttribute("products", products);
            model.addAttribute("title", categoryName);
        }

        double pageCount = Math.ceil((double)count / (double)perPage);  //broj strana

        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("perPage", perPage);
        model.addAttribute("count", count);
        model.addAttribute("page", page);

        return "products";
    }
}
