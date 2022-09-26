package com.leancoder.shopcart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.leancoder.shopcart.model.dao.ICategoryDao;
import com.leancoder.shopcart.model.dao.ISubCategoryDao;

@Controller
@RequestMapping("/items")
public class ShopItemsController {

    @Autowired
    ICategoryDao categoryDao;

    @Autowired
    ISubCategoryDao subCategoryDao;
    
    @GetMapping("/{category}/{subcategory}")
    public String ItemsByCategory(@PathVariable(required = true, name = "category") String category, @PathVariable(required = true, name = "subcategory") String subcategory, Model model) {
        var isCorrectRequest = categoryDao.existsCategoryByName(category).booleanValue() == true && subCategoryDao.existsSubcategoryByName(subcategory).booleanValue() == true ? true : false;
        if (!isCorrectRequest) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado.");
        }
        var allSubCategories = subCategoryDao.findAll();
        List<Object> productos = new ArrayList<>();
        allSubCategories.forEach(_subcategory -> {
            _subcategory.getProductTypes().iterator().forEachRemaining(productType -> {
                productType.getProducts().iterator().forEachRemaining(product -> {
                    // TODO: Filtrar productos repetidos(FILTRARLOS POR EL ID).
                    productos.add(product);
                });
            });
        });
        model.addAttribute("items", productos);
        return "items/list";
    }

}
