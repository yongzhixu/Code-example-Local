package com.convertlab.elasticsearch.services;

import com.convertlab.elasticsearch.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSearchService {

    private static final String PRODUCT_INDEX = "productindex";

    private ElasticsearchOperations elasticsearchOperations;

    public List<IndexedObjectInformation> createProductIndexBulk
            (final List<Product> products) {

        List<IndexQuery> queries = products.stream()
                .map(product->
                        new IndexQueryBuilder()
                                .withId(product.getId().toString())
                                .withObject(product).build())
                .collect(Collectors.toList());;

        return elasticsearchOperations
                .bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public String createProductIndex(Product product) {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(product.getId().toString())
                .withObject(product).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));

        return documentId;
    }


    /**
     * @description
     * -- NativeQuery
     *    NativeQuery provides the maximum flexibility for building a query using objects representing
     *    Elasticsearch constructs like aggregation, filter, and sort.
     *    Here is a NativeQuery for searching products matching a particular manufacturer:
     * @param brandName
     */
    public void findProductsByBrand(final String brandName) {

        QueryBuilder queryBuilder =
                QueryBuilders
                        .matchQuery("manufacturer", brandName);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<Product> productHits =
                elasticsearchOperations
                        .search(searchQuery,
                                Product.class,
                                IndexCoordinates.of(PRODUCT_INDEX));
    }

    /**
     * StringQuery
     * A StringQuery gives full control by allowing the use of the native Elasticsearch query as a JSON string as shown here:
     * @param productName
     */
    public void findByProductName(final String productName) {
        Query searchQuery = new StringQuery(
                "{\"match\":{\"name\":{\"query\":\""+ productName + "\"}}}\"");

        SearchHits<Product> products = elasticsearchOperations.search(
                searchQuery,
                Product.class,
                IndexCoordinates.of(PRODUCT_INDEX));
//  ...
    }

    /**
     * CriteriaQuery
     * With CriteriaQuery we can build queries without knowing any terminology of Elasticsearch.
     * The queries are built using method chaining with Criteria objects.
     * Each object specifies some criteria used for searching documents:
     * @param productPrice
     */
    public void findByProductPrice(final String productPrice) {
        Criteria criteria = new Criteria("price")
                .greaterThan(10.0)
                .lessThan(100.0);

        Query searchQuery = new CriteriaQuery(criteria);

        SearchHits<Product> products = elasticsearchOperations
                .search(searchQuery,
                        Product.class,
                        IndexCoordinates.of(PRODUCT_INDEX));
    }
}