package com.leancoder.shopcart.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.SubCategory;

@Repository
public interface ISubCategoryDao extends PagingAndSortingRepository<SubCategory, Long>{
    
    public Boolean existsSubcategoryByName(String name);

    public Optional<SubCategory> findByName(String name);

}
