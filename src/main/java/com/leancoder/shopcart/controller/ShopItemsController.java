package com.leancoder.shopcart.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.leancoder.shopcart.model.dao.ICategoryDao;
import com.leancoder.shopcart.model.dao.IProductTypeDao;
import com.leancoder.shopcart.model.dao.ISubCategoryDao;

@Controller
/* @RequestMapping("/items") */
public class ShopItemsController {

    @Autowired
    ICategoryDao categoryDao;

    @Autowired
    ISubCategoryDao subCategoryDao;

    @Autowired
    IProductTypeDao productTypeDao;
    
    @GetMapping("/{category}/{subcategory}")
    public String ItemsBySubCategory(@PathVariable(required = true, name = "category") String category, @PathVariable(required = true, name = "subcategory") String subcategory, Model model) {
        categoryDao.findByName(category).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        subCategoryDao.findByName(subcategory).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        var allSubCategories = subCategoryDao.findAll();
        List<Object> productos = new ArrayList<>();
        allSubCategories.forEach(_subcategory -> {
            _subcategory.getProductTypes().iterator().forEachRemaining(productType -> {
                productType.getProducts().iterator().forEachRemaining(product -> {
                    productos.add(product);
                });
            });
        });
        model.addAttribute("items", productos);
        return "items/list";
    }

    @GetMapping("/{category}/{subcategory}/{productType}")
    public String ItemsByProductType(@PathVariable(required = true, name = "category") String category, @PathVariable(required = true, name = "subcategory") String subcategory, @PathVariable(required = true, name="productType") String productType, Model model) {
        categoryDao.findByName(category).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        subCategoryDao.findByName(subcategory).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        productTypeDao.findByType(productType).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado"));
        var items = productTypeDao.findByType(productType).get().getProducts();
        model.addAttribute("items", items);
        return "items/list";
    }

}
