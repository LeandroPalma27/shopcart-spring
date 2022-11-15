package com.leancoder.shopcart.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// Entidad para los tipos de caracteristicas(COLOR, TAMAÑO, PESO, ETC)
// Esta entidad tiene una relacion de uno a muchos con la entidad "charactristicValues(EJEMPLOS DE TIPO DE CARACTERISTICA)".
// Sus campos serian el id, el nombre del tipo(COLOR, tamaño, conectividad, gamer), y la descripcion de estos tipos.

@Table(name = "characteristics")
@Entity
public class Characteristic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CharacteristicValue> characteristicValues;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CharacteristicValue> getCharateristicValue() {
        return characteristicValues;
    }

    public void setCharateristicValue(CharacteristicValue charateristicValue) {
        this.characteristicValues.add(charateristicValue);
    }

    

}
