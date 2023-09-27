package com.example.crud.repositories;

import com.example.crud.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProductRepository extends JpaRepository<Product,Long> {
}
