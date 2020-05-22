package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Category;
import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    //Category findBySlug(String slug);

   // Category findBySlugAndIdNot(String slug, Long id);

    Category findByName(String name);
}
