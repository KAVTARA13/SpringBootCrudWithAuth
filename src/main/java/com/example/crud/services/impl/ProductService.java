package com.example.crud.services.impl;

import com.example.crud.entities.Product;
import com.example.crud.repositories.IProductRepository;
import com.example.crud.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> listAll(){
        return productRepository.findAll();
    }
    @Override
    public void save(Product product){
        productRepository.save(product);
    }
    @Override
    public Optional<Product> get(Long id){
        return productRepository.findById(id);
    }
    @Override
    public void delete(Long id){
        productRepository.deleteById(id);
    }



}

