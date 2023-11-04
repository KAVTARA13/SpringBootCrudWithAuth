package com.example.crud.controllers;

import com.example.crud.entities.MyUserDetails;
import com.example.crud.entities.Users;
import com.example.crud.services.impl.CartServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class CartRestController {
    private final CartServices cartServices;
    @Autowired
    public CartRestController(CartServices cartServices) {
        this.cartServices = cartServices;
    }
    @PostMapping("/addToCart/add/{pid}/{qty}")
    public String addProductToCart(@PathVariable("pid") String productId,
                                   @PathVariable("qty") String quantity,
                                   @AuthenticationPrincipal Authentication authentication){
        Authentication authenticationa = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationa != null && !(authenticationa instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails =  (MyUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Users user = userDetails.getUser();
            Integer addedQuantity = cartServices.addProduct(Long.valueOf(productId),Integer.valueOf(quantity),user);
            return addedQuantity + " item(s) of this product were added to cart.";
        }else{
            return "You must login to add this product";
        }
    }

    @PostMapping("/addToCart/update/{pid}/{qty}")
    public String updateQuantity(@PathVariable("pid") String productId,
                                   @PathVariable("qty") String quantity,
                                   @AuthenticationPrincipal Authentication authentication){
        Authentication authenticationa = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationa != null && !(authenticationa instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails =  (MyUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Users user = userDetails.getUser();
            float subtotal = cartServices.updateQuantity(Integer.valueOf(quantity),Long.valueOf(productId),user);
            return String.valueOf(subtotal) ;
        }else{
            return "You must login to add this product";
        }
    }
    @PostMapping("/addToCart/remove/{pid}")
    public String removeProduct(@PathVariable("pid") String productId,
                                 @AuthenticationPrincipal Authentication authentication){
        Authentication authenticationa = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationa != null && !(authenticationa instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails =  (MyUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Users user = userDetails.getUser();
            cartServices.removeProduct(Long.valueOf(productId),user);
            return "The product has been removed .";
        }else{
            return "You must login to add this product";
        }
    }
}
