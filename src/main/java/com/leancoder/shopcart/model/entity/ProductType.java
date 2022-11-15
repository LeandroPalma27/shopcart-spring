package com.leancoder.shopcart.model.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

// Entidad para los productTypes(TIPOS DE PRODUCTO)

/*
 * Sera el tercer y ultimo nivel de cada producto.
 * Esta entidad tiene una relacion de muchos a muchos con los productos(UN PRODUCTO PUEDE SER PARTE DE LAPTOPS Y LAPTOPS GAMER).
 * Esta entidad tiene una relacion de muchos a uno con la subcategoria.
 * Tendra otra relacion de uno a muchos con la entidad productTypeCharacteristicValue(PARA LA CONSULTA DE TODAS LAS CARACTERISTICAS DISPONIBLES POR EL TIPO DE PRODUCTO, CON SU RESPECTIVO VALOR).
 * Solo tendra como campos el id, nombre deL tipo de producto y el id de la subcategoria.
*/
@Table(name = "productTypes")
@Entity
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 2)
    @Max(value = 80)
    private String type;

    @ManyToMany(mappedBy = "productTypes")
    private Set<Product> products;

    @ManyToOne(targetEntity = SubCategory.class)
    @JoinColumn(name = "subcategory_id", nullable = true, referencedColumnName = "id")
    private SubCategory subcategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productType")
    private List<ProductTypeCharacteristicValue> productTypeCharacteristicValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Product product) {
        this.products.add(product);
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }

    public List<ProductTypeCharacteristicValue> getProductTypeCharacteristicValues() {
        return productTypeCharacteristicValues;
    }

    public void setProductTypeCharacteristicValues(ProductTypeCharacteristicValue productTypeCharacteristicValue) {
        this.productTypeCharacteristicValues.add(productTypeCharacteristicValue);
    }

}
