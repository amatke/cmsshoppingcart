package com.matovic.cmsshoppingcart;

import com.matovic.cmsshoppingcart.models.CategoryRepository;
import com.matovic.cmsshoppingcart.models.PageRepository;
import com.matovic.cmsshoppingcart.models.entities.Cart;
import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice       // ova klasa presrece sve ostale pozive kontrolera (prvo se ovo izvrsi)
public class Common {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute
    public void sharedData(HttpSession session, Model model){

        List<Page> pages = pageRepository.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepository.findAll();

        boolean cartActive = false;

        if(session.getAttribute("cartMap") != null){
            HashMap<Integer, Cart> cartMap = (HashMap<Integer, Cart>) session.getAttribute("cartMap");

            int quantity = 0;
            double totalPrice = 0;

            for (Cart c : cartMap.values()) {
                quantity += c.getQuantity();
                totalPrice += c.getQuantity() * Double.parseDouble(c.getPrice());
            }
            model.addAttribute("cquantity", quantity);
            model.addAttribute("ctotalPrice", totalPrice);

            cartActive = true;
        }

        model.addAttribute("cartActive", cartActive);
        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);
    }
}
