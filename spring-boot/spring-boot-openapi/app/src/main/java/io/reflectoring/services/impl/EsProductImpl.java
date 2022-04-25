package io.reflectoring.services.impl;

import com.convertlab.elasticsearch.repositories.ProductRepository;
import com.convertlab.elasticsearch.services.ProductSearchService;
import io.reflectoring.api.EsApiDelegate;
import io.reflectoring.bean.ProductComparator;
import io.reflectoring.bean.ProductD2R;
import io.reflectoring.bean.ProductR2D;
import io.reflectoring.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EsProductImpl implements EsApiDelegate {


    private ProductRepository productRepository;

    private ProductSearchService productSearchService;

    @Autowired
    public EsProductImpl(ProductRepository productRepository, ProductSearchService productSearchService) {
        this.productRepository = productRepository;
        this.productSearchService = productSearchService;
    }

    @Override
    public ResponseEntity<String> saveProduct(Product body) {
        try {
            productRepository.save(new ProductR2D().converterA2B(body));
        }catch (Exception e){
            if (!e.getMessage().contains("ok")&!e.getMessage().contains("created")&!e.getMessage().contains("updated")){
                log.info(e.getMessage());
            }else {
                log.error(e.getMessage());
                return ResponseEntity.status(500).body(null);
            }
        }
//        productSearchService.saveProduct(new ProductR2D().converterA2B(body));
        return ResponseEntity.ok("Succeed in saving product!");
    }

    @Override
    public ResponseEntity<Product> searchProduct(String body) {
        return EsApiDelegate.super.searchProduct(body);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            products.add(new ProductD2R().converterA2B(product));
        });
        ProductComparator productComparator = new ProductComparator();
        products.sort(productComparator);
        return ResponseEntity.ok().body(products);
    }
}
