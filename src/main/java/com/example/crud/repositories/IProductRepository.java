package com.example.crud.repositories;

import com.example.crud.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where " +
            "concat(p.id,' ',p.name,' ',p.brand,' ',p.madeIn,' ',p.price) " +
            "like %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);
}
