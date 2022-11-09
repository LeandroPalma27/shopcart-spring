package com.leancoder.shopcart.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.leancoder.shopcart.model.entity.Product;

//Repository de la entidad PRODUCTOS con la interfaz JpaRepository
public interface IProductoDao extends PagingAndSortingRepository<Product, Long>{

    public Page<Product> findByProductTypes_Id(Long id, Pageable pageable);

    public Page<Product> findByProductTypes_Type(String type, Pageable pageable);


}
