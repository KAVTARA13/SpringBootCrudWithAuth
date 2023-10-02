package com.example.crud.services.impl;

import com.example.crud.entities.Product;
import com.example.crud.repositories.IProductRepository;
import com.example.crud.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final IProductRepository productRepository;
    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Page<Product> listAll(int pageNumber,String sortField,String sortDir,String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber-1,5,sort);
        if (keyword != null){
            return productRepository.findAll(keyword,pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product){
        productRepository.save(product);
    }
    @Override
    public Optional<Product> get(Long id){
        LOGGER.warn("Enter method get");
        LOGGER.info("Enter method get");
        Optional<Product> product = productRepository.findById(id);
        LOGGER.warn("Product: "+product);
        return product;
    }
    @Override
    public void delete(Long id){
        productRepository.deleteById(id);
    }



}

