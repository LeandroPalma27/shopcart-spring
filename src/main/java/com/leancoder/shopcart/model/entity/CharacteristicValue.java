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

// Entidad para los ejemplos de cada tipo de caracteristica.
// Sus campos serian el id, el nombre del ejemplo(ROJO, RGB, SI, NO, HIBRIDO), y la descripcion de estos ejemplos(SI - tiene bluetooh, NO - no tiene bluetooh)
@Table(name = "characteristicValues")
@Entity
public class CharacteristicValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String descriptionValue;

    @ManyToOne(targetEntity = Characteristic.class)
    @JoinColumn(name = "characteristic_id", nullable = true, referencedColumnName="id")
    private Characteristic characteristic;

    @ManyToMany(mappedBy = "characteristicValues")
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "characteristicValue")
    private List<ProductTypeCharacteristicValue> ProductTypeCharacteristicValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescriptionValue() {
        return descriptionValue;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Product product) {
        this.products.add(product);
    }

    public void setDescriptionValue(String descriptionValue) {
        this.descriptionValue = descriptionValue;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public List<ProductTypeCharacteristicValue> getProductTypeCharacteristicValue() {
        return ProductTypeCharacteristicValues;
    }

    public void setProductTypeCharacteristicValue(ProductTypeCharacteristicValue productTypeCharacteristicValue) {
        ProductTypeCharacteristicValues.add(productTypeCharacteristicValue);
    }

    

}
