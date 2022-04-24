package com.convertlab.elasticsearch.repositories;

import com.convertlab.elasticsearch.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository  extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByManufacturerAndCategory
            (String manufacturer, String category);

    List<Product> findByDocTitleEndsWith(String name);
    List<Product> findByDocTitleStartsWith(String name);
    List<Product> findByDocTypeEndsWith(String name);
    List<Product> findByDocTypeStartsWith(String name);
}
