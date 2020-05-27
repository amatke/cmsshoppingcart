package com.matovic.cmsshoppingcart;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.PageRepository;
import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice       // ova klasa presrece sve ostale pozive kontrolera (prvo se ovo izvrsi)
public class Common {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute
    public void sharedData(Model model){

        List<Page> pages = pageRepository.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);
    }
}
