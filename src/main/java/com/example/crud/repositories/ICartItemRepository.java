package com.example.crud.repositories;

import com.example.crud.entities.CartItem;
import com.example.crud.entities.Product;
import com.example.crud.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem,Integer> {
    public List<CartItem> findByUser(Users user);
    public CartItem findByUserAndProduct(Users user, Product product);

    @Query("UPDATE CartItem c SET c.quantity = ?1 where c.product.id = ?2 and " +
            "c.user.id = ?3")
    @Modifying
    public void updateQuantity(Integer quantity,Long productId,Long userId);
    @Query("delete from CartItem c where c.user.id = ?1 and c.product.id = ?2")
    @Modifying
    public void deleteByUserAndProduct(Long userId,Long productId);
}
