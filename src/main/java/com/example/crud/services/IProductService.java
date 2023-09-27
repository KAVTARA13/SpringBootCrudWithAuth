package com.example.crud.services;

import com.example.crud.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Page<Product> listAll(int pageNumber,String sortField,String sortDir);
    public void save(Product product);
    public Optional<Product> get(Long id);
    public void delete(Long id);
}
