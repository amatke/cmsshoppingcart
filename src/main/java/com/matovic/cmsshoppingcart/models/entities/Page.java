package com.matovic.cmsshoppingcart.models.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Entity
@Table(name = "pages")
@Data // ovo nam omogucama lombok da imamo getere i setere
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=2, message = "Tittle must be at least 2 characters lon")
    @NotNull
    private String title;

    private String slug;

    @Size(min=5, message = "Tittle must be at least 5 characters lon")
    @NotNull
    private String content;

    private int sorting;

    @Override
    public String toString() {
        return new StringJoiner(", ", Page.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("slug='" + slug + "'")
                .add("content='" + content + "'")
                .add("sorting=" + sorting)
                .toString();
    }
}
