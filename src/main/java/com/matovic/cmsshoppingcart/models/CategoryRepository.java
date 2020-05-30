package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    Category findBySlug(String slug);

    List<Category> findAllByOrderBySortingAsc();

    List<Category> findAllByOrderByNameAsc();
}
