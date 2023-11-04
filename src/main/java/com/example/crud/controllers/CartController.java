package com.example.crud.controllers;

import com.example.crud.entities.CartItem;
import com.example.crud.entities.MyUserDetails;
import com.example.crud.entities.Users;
import com.example.crud.services.impl.CartServices;
import com.example.crud.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {
    private final CartServices cartServices;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public CartController(CartServices cartServices, UserDetailsServiceImpl userDetailsService) {
        this.cartServices = cartServices;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/cart")
    public String showShoppingCart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Users user = userDetails.getUser();
        List<CartItem> cartItems = cartServices.listCartItems(user);
        model.addAttribute("cartItems",cartItems);
        return "cart";
    }
}
