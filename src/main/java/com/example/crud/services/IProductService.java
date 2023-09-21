package com.example.crud.services;

import com.example.crud.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public List<Product> listAll();
    public void save(Product product);
    public Optional<Product> get(Long id);
    public void delete(Long id);
}
