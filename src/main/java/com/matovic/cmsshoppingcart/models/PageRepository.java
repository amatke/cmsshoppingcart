package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findBySlug(String slug);           // pretrazuje Slug po prosledjenom slug
}
