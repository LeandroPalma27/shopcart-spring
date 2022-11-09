package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.SubCategory;

//Repository de la entidad Subcategoria (con la interfaz JpaRepository)
@Repository
public interface ISubCategoryDao extends PagingAndSortingRepository<SubCategory, Long>{
    
    // Para ver si existe la subcategoria en la BD, a traves del nombre:
    public Boolean existsSubcategoryByName(String name);

    // Para buscar la subcategoria a traves del nombre(SE RETORNA EL OBJETO QUE SERIALIZA CATEGORIA):
    public Optional<SubCategory> findByName(String name);

}
