package com.example.crud.controllers;

import com.example.crud.entities.Product;
import com.example.crud.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService =  productService;
    }
    @RequestMapping("/")
    public String viewHomePage(Model model,Authentication authentication){
        List<Product> productList = productService.listAll();
        model.addAttribute("listProducts",productList);

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model){
        Product product = new Product();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        model.addAttribute("product",product);
        return "new_product";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/";
    }
    @RequestMapping(value = "/edit_save",method = RequestMethod.POST)
    public String saveEditProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showNewProductForm(@PathVariable("id")Long id,Model model){
        ModelAndView mav = new ModelAndView("edit_product");
        Optional<Product> product = productService.get(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        mav.addObject("product",product);
        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")Long id){
        productService.delete(id);
        return "redirect:/";
    }
}
