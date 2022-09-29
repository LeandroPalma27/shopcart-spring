package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leancoder.shopcart.model.entity.ProductType;

public interface IProductTypeDao extends PagingAndSortingRepository<ProductType, Long>{
    
    public Optional<ProductType> findByType(String type);

}
