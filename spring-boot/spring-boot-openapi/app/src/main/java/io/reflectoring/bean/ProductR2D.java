package io.reflectoring.bean;


import com.convertlab.common.beta.bean.AppConverter;
import com.convertlab.elasticsearch.model.ProductDocument;
import io.reflectoring.model.Product;
import org.springframework.beans.BeanUtils;

public class ProductR2D extends AppConverter<Product, ProductDocument> {
    public ProductR2D() {
        super(product -> {
            ProductDocument productDocument = new ProductDocument();
            BeanUtils.copyProperties(product, productDocument);
            return productDocument;
        });
    }
}
