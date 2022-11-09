package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leancoder.shopcart.model.entity.ProductType;

//Repository de la entidad productType(PARA LOS TIPOS DE PRODUCTOS) con la interfaz JpaRepository
public interface IProductTypeDao extends PagingAndSortingRepository<ProductType, Long>{
    
    // Para buscar un tipo de producto por su nombre de tipo:
    public Optional<ProductType> findByType(String type);

}
