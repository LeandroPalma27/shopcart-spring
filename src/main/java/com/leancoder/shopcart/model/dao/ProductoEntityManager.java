package com.leancoder.shopcart.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.leancoder.shopcart.model.entity.Product;

@Repository
public class ProductoEntityManager {

    @PersistenceContext
    EntityManager entityManager;

    // TODO: Documentar metodo EntityManager que busca los productos con la query final(QUE PUEDE O NO TENER FILTROS NORMALES, ESPECIAL 1 O ESPECIAL 2).
    public Map<String, List<Product>> findProductsWithCharacteristics(PageRequest pageRequest, String readyQuery) {

        //List<Product> productos = new ArrayList<Product>();
        /* for (var readyQuery : readyQueries) {
            //System.out.println(readyQuery);
            Query query = entityManager.createNativeQuery(readyQuery, Product.class);
            int pageNumber = pageRequest.getPageNumber();
            int pageSize = pageRequest.getPageSize();
            query.setFirstResult((pageNumber) * pageSize);
            query.setMaxResults(pageSize);
            productos.addAll(query.getResultList());
        } */
        Map<String, List<Product>> data = new HashMap<String, List<Product>>();
        Query query = entityManager.createNativeQuery(readyQuery,
        Product.class);
        data.put("dataNotPaged", query.getResultList());
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Product> productos = query.getResultList();
        data.put("dataPaged", productos);
        return data;

       /*  if (readyQueries.size() > 1) {
            List<Product> productosFiltrados = new ArrayList<>();
            List<Product> productosFiltrados2 = new ArrayList<>();
            for (var producto : productos) {
                if (!productosFiltrados.contains(producto)) {
                    productosFiltrados.add(producto);
                } else {
                    productosFiltrados2.add(producto);
                    continue;
                }
            }
            return ultimoFiltro(productosFiltrados2);
        }
        return productos;
        
    }

    public List<Product> ultimoFiltro(List<Product> productos) {
        List<Product> productosFiltrados = new ArrayList<>();
        for (var producto : productos) {
            if (!productosFiltrados.contains(producto)) {
                productosFiltrados.add(producto);
            } else {
                continue;
            }
        }
        return productosFiltrados; */
    }
}
