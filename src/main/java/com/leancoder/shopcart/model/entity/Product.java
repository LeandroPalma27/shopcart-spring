package com.leancoder.shopcart.model.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productos")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0)
    private Long stock;

    @DecimalMin(value = "1.0")
    private Double price;

    @Size(max = 120, min = 4, message = "La cantidad minima de caracteres de 4 y la maxima 120.")
    private String title;

    private String code;

    @Size(max = 250)
    private String description;

    @Min(value = 0)
    @Max(value = 5)
    private Integer rating;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "productTypes_asigned", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "productType_id"))
    private Set<ProductType> productTypes;

    @ManyToMany
    @JoinTable(name = "characteristicValues_asigned", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "characteristicValue_id"))
    private Set<CharacteristicValue> characteristicValues;

    /* @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductCharacteristicValues> productCharacteristicValues; */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    /* public List<ProductCharacteristicValues> getProductCharacteristicValues() {
        return productCharacteristicValues;
    }

    public void setProductCharacteristicValues(ProductCharacteristicValues productCharacteristicValue) {
        this.productCharacteristicValues.add(productCharacteristicValue);
    } */

    public Set<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(ProductType productType) {
        this.productTypes.add(productType);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public Set<CharacteristicValue> getCharacteristicValues() {
        return characteristicValues;
    }

    public void setCharacteristicValues(Set<CharacteristicValue> characteristicValues) {
        this.characteristicValues = characteristicValues;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
    }

    public Boolean productHasNotComments() {
        if (this.comments == null) {
            return true;
        }
        return false;
    }

}
