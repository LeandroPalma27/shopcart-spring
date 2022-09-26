package com.leancoder.shopcart.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.SubCategory;

@Repository
public interface ISubCategoryDao extends PagingAndSortingRepository<SubCategory, Long>{
    
    public Boolean existsSubcategoryByName(String name);

}
