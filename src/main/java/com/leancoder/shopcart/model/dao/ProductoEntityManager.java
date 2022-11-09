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

// Repository de la entidad de productos(CON ENTITY MANAGER)
@Repository
public class ProductoEntityManager {

    @PersistenceContext
    EntityManager entityManager;

    // Este metodo se encarga de buscar los productos, recibiendo un sql query funcional para la consulta de productos en la base de datos. 
    // Tambien se recibe el pageRequest que se encarga de manejar el paginado.
    // SE RETORA UN MAP CON UNA LISTA DE TODOS LOS PRODUCTOS ENCONTRADOS Y OTRA SOLO CON UNA PARTE DE ELLOS, YA QUE ESTAN PAGINADOS.
    public Map<String, List<Product>> findProductsComplex(PageRequest pageRequest, String readyQuery) {
        Map<String, List<Product>> data = new HashMap<String, List<Product>>();
        Query query = entityManager.createNativeQuery(readyQuery,
        Product.class);
        // Cargamos todos los productos encontrados sin la paginacion:
        data.put("dataNotPaged", query.getResultList());

        // Codigo encargado del paginado:
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);

        // Cargamos todos los productos encontrados pero con la paginacion inlcuida:
        List<Product> productos = query.getResultList();
        data.put("dataPaged", productos);
        return data;
    }
}
