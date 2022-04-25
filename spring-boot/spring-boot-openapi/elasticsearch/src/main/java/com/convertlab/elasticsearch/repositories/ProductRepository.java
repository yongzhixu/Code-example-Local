package com.convertlab.elasticsearch.repositories;

import com.convertlab.elasticsearch.model.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository  extends ElasticsearchRepository<ProductDocument, String> {
    List<ProductDocument> findByName(String name);

    List<ProductDocument> findByNameContaining(String name);

    List<ProductDocument> findByManufacturerAndCategory
            (String manufacturer, String category);

    List<ProductDocument> findByNameEndsWith(String name);
    List<ProductDocument> findByNameStartsWith(String name);
}
