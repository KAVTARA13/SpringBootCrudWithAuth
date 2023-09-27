package com.example.crud.services.impl;

import com.example.crud.entities.Product;
import com.example.crud.repositories.IProductRepository;
import com.example.crud.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Product> listAll(int pageNumber,String sortField,String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber-1,5,sort);
        return productRepository.findAll(pageable);
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

