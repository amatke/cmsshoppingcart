package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);

    Product findByName(String name);

    // da li postoji ovakav slug ali da id nisu isti
    Product findBySlugAndIdNot(String slug, Long id);   // za edit
}
