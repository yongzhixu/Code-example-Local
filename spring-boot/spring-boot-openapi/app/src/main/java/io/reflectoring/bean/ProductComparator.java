package io.reflectoring.bean;

import io.reflectoring.model.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

    @Override
    public int compare(Product product, Product otherProduct) {
        return product.getPrice().compareTo(otherProduct.getPrice());
    }
}
