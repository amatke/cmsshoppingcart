package com.matovic.cmsshoppingcart.models.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Name must be at least 2 characters long!")
    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String slug;

    @Size(min = 5, message = "Description must be at least 5 characters long!")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(length = 45, nullable = false)
    private String image;

    @Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "Expected format: 5, 5.99, 15, 15.99")
    @Column(length = 45, nullable = false)
    private String price;       // to be validated for decimal(8,2)

    //@Pattern(regexp = "^[1-9][0-9]*", message = "Please choose a category")
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp                      // automatski se inicijalizuje ovo polje kada se kreira (hibernate omogucava ovo)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp                        // automatski se azurira ovo polje kada se kreira (hibernate omogucava ovo)
    private LocalDateTime updatedAt;
}
