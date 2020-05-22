package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
