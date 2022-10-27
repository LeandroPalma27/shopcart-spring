package com.leancoder.shopcart.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.leancoder.shopcart.model.entity.Product;

public interface IProductoDao extends PagingAndSortingRepository<Product, Long>{

    @Query(value = "SELECT * FROM productos join product_types where product_types.subcategory_id = :id group by productos.id", nativeQuery = true)
    public List<Product> findBySubcategoryId(@Param("id") Long id, Pageable pageable);

    public Page<Product> findByProductTypes_Id(Long id, Pageable pageable);

    public Page<Product> findByProductTypes_Type(String type, Pageable pageable);

    public Page<Product> findByCharacteristicValues_DescriptionValue(String value, Pageable pageable);
    
    public Page<Product> findByCharacteristicValues_DescriptionValueAndProductTypes_Type(String value, String type, Pageable pageable);

    // TODO: Hacer que el query sirva para varios filtros(¿EntityManager con paginacion?, ¿Otro metodo de paginacion?)
    @Query(nativeQuery = true, value = "SELECT productos.id, productos.code, productos.description, productos.price, productos.rating, productos.stock, productos.title FROM productos left outer join characteristic_values_asigned on productos.id = characteristic_values_asigned.product_id left outer join characteristic_values on characteristic_values_asigned.characteristic_value_id = characteristic_values.id where characteristic_values.description_value = 'Color-negro' and characteristic_values.description_value = :query")
    public Page<Product> findByCharacteristicValues_DescriptionValues(String query, Pageable pageable);

}
