package com.leancoder.shopcart.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "productTypeCharacteristicValues")
@Entity
public class ProductTypeCharacteristicValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = CharacteristicValue.class)
    @JoinColumn(name = "characteristicValue_id", nullable = true, referencedColumnName="id")
    private CharacteristicValue characteristicValue;

    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn(name = "productType_id", nullable = true, referencedColumnName="id")
    private ProductType productType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacteristicValue getCharacteristicValue() {
        return characteristicValue;
    }

    public void setCharacteristicValue(CharacteristicValue characteristicValue) {
        this.characteristicValue = characteristicValue;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

}
