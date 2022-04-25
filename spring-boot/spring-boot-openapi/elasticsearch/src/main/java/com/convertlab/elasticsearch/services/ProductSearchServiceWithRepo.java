package com.convertlab.elasticsearch.services;

import com.convertlab.elasticsearch.model.ProductDocument;
import com.convertlab.elasticsearch.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceWithRepo {
    private ProductRepository productRepository;

    public void createProductIndexBulk(final List<ProductDocument> products) {
        productRepository.saveAll(products);
    }

    public void createProductIndex(final ProductDocument product) {
        productRepository.save(product);
    }

}
