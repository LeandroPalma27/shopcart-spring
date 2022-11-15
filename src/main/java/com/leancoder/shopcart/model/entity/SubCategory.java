package com.leancoder.shopcart.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

// Entidad para las subcategorias

/*
 * Sera el segundo nivel de cada producto.
 * Esta entidad tiene una relacion de muchos a uno con la entidad "categoria".
 * Esta entidad tiene una relacion de uno a muchos con la entidad "productTypes(TIPO DE PRODUCTO)".
 * Solo tendra como campos el id, nombre de la subcategoria y el id de la categoria.
*/
@Table(name = "subcategory")
@Entity
public class SubCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 80)
    @Column(unique = true)
    private String name;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "subcategory")
    private List<ProductType> productTypes;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = true, referencedColumnName="id")
    private Category category;

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

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(ProductType productType) {
        this.productTypes.add(productType);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
