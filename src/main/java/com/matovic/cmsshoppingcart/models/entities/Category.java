package com.matovic.cmsshoppingcart.models.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "Name must be at least 2 characters long")
    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String slug;

    @NotNull
    private int sorting;


}
