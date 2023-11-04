package com.example.crud.services.impl;

import com.example.crud.entities.CartItem;
import com.example.crud.entities.Product;
import com.example.crud.entities.Users;
import com.example.crud.repositories.ICartItemRepository;
import com.example.crud.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServices {
    private final ICartItemRepository cartItemRepository;
    private final IProductRepository productRepository;
    @Autowired
    public CartServices(ICartItemRepository cartItemRepository, IProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> listCartItems(Users user){
        return cartItemRepository.findByUser(user);
    }
    public Integer addProduct(Long productId,Integer quantity,Users user){
        Integer addedQuantity = quantity;
        Product product = productRepository.findById(productId).get();
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user,product);
        if (cartItem != null){
            addedQuantity = cartItem.getQuantity()+quantity;
            cartItem.setQuantity(addedQuantity);
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
        return  addedQuantity;
    }
    public  float updateQuantity(Integer quantity,Long productId,Users user){
        cartItemRepository.updateQuantity(quantity,productId, user.getId());
        Product product = productRepository.findById(productId).get();
        float subtotal = product.getPrice()*quantity;
        return subtotal;
    }
    public void removeProduct(Long productId,Users user){
        cartItemRepository.deleteByUserAndProduct(user.getId(),productId);
    }
}
