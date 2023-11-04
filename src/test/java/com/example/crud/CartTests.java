package com.example.crud;

import com.example.crud.entities.CartItem;
import com.example.crud.entities.Product;
import com.example.crud.entities.Users;
import com.example.crud.repositories.ICartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CartTests {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddCartItem(){
        Product product = entityManager.find(Product.class,10);
        Users users = entityManager.find(Users.class,2);

        CartItem cartItem = new CartItem();
        cartItem.setUser(users);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        assertTrue(savedCartItem.getId()>0);
    }
    @Test
    public void testGetCartItemsByUser(){
        Users user = new Users();
        user.setId(2L);

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        assertEquals(2,cartItems.size());
        System.out.println(cartItems);
    }
}
