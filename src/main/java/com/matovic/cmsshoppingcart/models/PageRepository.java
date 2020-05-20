package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository extends JpaRepository<Page, Long> {

    Page findBySlug(String slug);           // pretrazuje Slug po prosledjenom slug

   // @Query("SELECT p FROM Page p WHERE p.id != :id AND p.slug = :slug")       // 1. nacin (legacy)
   // Page findBySlug(Long id, String slug);

    Page findBySlugAndIdNot(String slug, Long id);                  // 2. nacin (aktuelan)
}
