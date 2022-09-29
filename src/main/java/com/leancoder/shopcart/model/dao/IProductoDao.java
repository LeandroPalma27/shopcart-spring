package com.leancoder.shopcart.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leancoder.shopcart.model.entity.Product;

public interface IProductoDao extends PagingAndSortingRepository<Product, Long>{
    
}
