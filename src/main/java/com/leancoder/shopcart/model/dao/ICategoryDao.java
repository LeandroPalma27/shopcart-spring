package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.Category;

//Repository de la entidad Categoria (con la interfaz JpaRepository)
@Repository
public interface ICategoryDao extends PagingAndSortingRepository<Category, Long>{
    
    // Para ver si existe la categoria en la BD, a traves del nombre:
    public Boolean existsCategoryByName(String name);

    // Para buscar la categoria a traves del nombre(SE RETORNA EL OBJETO QUE SERIALIZA CATEGORIA):
    public Optional<Category> findByName(String name);

}
