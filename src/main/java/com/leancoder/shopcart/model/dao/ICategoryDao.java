package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.Category;

@Repository
public interface ICategoryDao extends PagingAndSortingRepository<Category, Long>{
    
    public Boolean existsCategoryByName(String name);

    public Optional<Category> findByName(String name);

}
