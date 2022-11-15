package com.leancoder.shopcart.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

// Entidad para las categorias

/*
 * Sera el primer nivel de cada producto.
 * Esta entidad tiene una relacion de uno a muchos con la entidad "subcategoria".
 * Solo tendra como campos el id y nombre de la categoria.
*/
@Table(name = "category")
@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 3)
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
    private List<SubCategory> subcategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubcategory() {
        return subcategories;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategories.add(subcategory);
    }
    
}
