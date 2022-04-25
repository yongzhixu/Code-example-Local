package io.reflectoring.bean;


import com.convertlab.common.beta.bean.AppConverter;
import com.convertlab.elasticsearch.model.ProductDocument;
import io.reflectoring.model.Product;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

public class ProductD2R extends AppConverter<ProductDocument, Product> {
    public ProductD2R() {
        super(productDocument -> {
            Product product =  new Product();
            BeanUtils.copyProperties(productDocument,product);
            return product;
        });
    }
}
