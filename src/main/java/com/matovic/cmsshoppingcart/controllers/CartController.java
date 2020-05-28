package com.matovic.cmsshoppingcart.controllers;

import com.matovic.cmsshoppingcart.models.ProductRepository;
import com.matovic.cmsshoppingcart.models.entities.Cart;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, HttpSession session, Model model,
                      @RequestParam(value = "cartPage", required = false) String cartPage ){

        Product product = productRepository.getOne((long)id);

        if (session.getAttribute("cartMap") == null){

            HashMap<Integer, Cart> cartMap = new HashMap<>();
            cartMap.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            session.setAttribute("cartMap", cartMap);
        } else{
            HashMap<Integer, Cart> cartMap = (HashMap<Integer, Cart>) session.getAttribute("cartMap");

            if(cartMap.containsKey(id)){
                int quantity = cartMap.get(id).getQuantity();
                cartMap.put(id, new Cart(id, product.getName(), product.getPrice(), ++quantity, product.getImage()));
            } else{
                cartMap.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
                session.setAttribute("cartMap", cartMap);
            }
        }

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cartMap");

        int size = 0;
        double total = 0;

        for (Cart value : cart.values()) {
            size += value.getQuantity();
            total += value.getQuantity() * Double.parseDouble(value.getPrice());
        }

        model.addAttribute("size", size);
        model.addAttribute("total", total);

        if(cartPage != null){   // cartPage se setuje samo na digme +
            return "redirect:/cart/view";
        }
        return "cart_view";
    }


    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model){
        if(session.getAttribute("cartMap") == null){
            return "redirect:/";
        }

        HashMap<Integer, Cart> cartMap = (HashMap<Integer, Cart>) session.getAttribute("cartMap");
        model.addAttribute("cartMap", cartMap);
        model.addAttribute("hideCartInfo", true);

        return "cart";
    }

    @GetMapping("/subtract/{id}")
    public String subtract(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        HashMap<Integer, Cart> cartMap = (HashMap<Integer, Cart>) session.getAttribute("cartMap");
        Cart cart = cartMap.get(id);

        if(cart.getQuantity() == 1){
            cartMap.remove(id);
            if(cartMap.isEmpty()){
                session.removeAttribute("cartMap");
            }
        } else {
            //cart.put(id, new Cart(id, product.getName(), product.getPrice(), --qty, product.getImage()));
            cartMap.put(id, new Cart(id, cart.getName(), cart.getPrice(), cart.getQuantity()-1, cart.getImage()));
        }

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;       // ovo znaci da se vracamo na stanicu odakle je dosao request
    }



    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        HashMap<Integer, Cart> cartMap = (HashMap<Integer, Cart>) session.getAttribute("cartMap");

        if(cartMap.size() == 1){
            session.removeAttribute("cartMap");
        } else {
            cartMap.remove(id);
            session.setAttribute("cartMap", cartMap);
        }

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;       // ovo znaci da se vracamo na stanicu odakle je dosao request
    }

    @GetMapping("/clear")
    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {

        session.removeAttribute("cartMap");

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;       // ovo znaci da se vracamo na stanicu odakle je dosao request
    }

}
