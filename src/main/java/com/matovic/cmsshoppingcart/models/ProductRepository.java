package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);

    Product findByName(String name);

    // da li postoji ovakav slug ali da id nisu isti
    Product findBySlugAndIdNot(String slug, Long id);   // za edit

    List<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    long countByCategoryId(Long categoryId);
}
