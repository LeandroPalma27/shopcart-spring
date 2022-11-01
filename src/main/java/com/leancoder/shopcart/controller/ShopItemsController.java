package com.leancoder.shopcart.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.leancoder.shopcart.model.dao.ICategoryDao;
import com.leancoder.shopcart.model.dao.IProductTypeDao;
import com.leancoder.shopcart.model.dao.IProductoDao;
import com.leancoder.shopcart.model.dao.ISubCategoryDao;
import com.leancoder.shopcart.model.dao.ProductoEntityManager;
import com.leancoder.shopcart.model.entity.CharacteristicValue;
import com.leancoder.shopcart.model.entity.Product;

// CONTROLADOR PARA EL MANEJO DE PRODUCTOS DE LA TIENDA:
/*  
 * Este controlador maneja los productos, con endpoints para consultar los productos por subcategoria o por tipo de producto.
 */
@Controller
@RequestMapping("/items")
public class ShopItemsController {

    // Inyeccion del DAO de categoria:
    /*
     * Se usa para consultar si una categoria ingresada(a traves del path) existe.
     */
    @Autowired
    ICategoryDao categoryDao;

    // Inyeccion del DAO de subcategoria:
    /*
     * Se usa para consultar si una subcategoria ingresada(a traves del path)
     * existe.
     */
    @Autowired
    ISubCategoryDao subCategoryDao;

    // Inyeccion del DAO de productType:
    /*
     * Se usa para consultar si un tipo de producto ingresado(a traves del path)
     * existe.
     */
    @Autowired
    IProductTypeDao productTypeDao;

    // Inyeccion del DAO de productos:
    /*
     * Se usa para listar(con paginacion) a los productos por el tipo de producto.
     */
    @Autowired
    IProductoDao productoDao;

    // Inyeccion del DAOEntityManager de productos:
    /*
     * Se usa para usar consultas hechas con Entity Manager, para la entidad
     * producto.
     */
    @Autowired
    ProductoEntityManager productoEntityManager;

    // ENDPOINTS:

    // Endopoint para redireccionar a una carga de todos los productos por una
    // subcategoria:
    /*
     * Recibe dos parametros en el path, el primero para la categoria y el segundo
     * para la subcategoria.
     * Verifica que existan los registros ingresados por el path(CASO CONTRARIO
     * GENERA UNA EXCEPCION DE ERROR HTTP 404).
     * Si todo sale con exito, redirige a un path que liste a todos los productos
     * por una subcategoria, a traves del productType "ver-todo-(SUBCATEGORIA)",
     * quedando asi: "/tecnologia/computo/ver-todo-computo"
     */
    @GetMapping("/{category}/{subcategory}")
    public String ItemsBySubCategory(@PathVariable(required = true, name = "category") String category,
            @PathVariable(required = true, name = "subcategory") String subcategory, Model model) {
        // Generamos un excepcion HTTP de tipo 404, en caso de no encontrar la categoria:
        categoryDao.findByName(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        // Y si la cateogria se encuentra, se procede a buscar la subcategoria. Si no se encuentra tambien generamos una excepcion HTTP 404:    
        subCategoryDao.findByName(subcategory)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        return "redirect:/items/".concat(category + "/").concat(subcategory + "/ver-todo-").concat(subcategory);
    }

    // Endpoint para redireccionar a una carga de todos los productos por un tipo de producto(PODRIA SER UN "ver-todo-subcategoria"):
    /*
     * Recibe 3 parametros en el path, el primero para la categoria, el segundo para la subcategoria y el tercero para el tipo de producto.
     * Verifica que existan los registros ingresados por el path(CASO CONTRARIO
     * GENERA UNA EXCEPCION DE ERROR HTTP 404).
     * Si todo sale con exito, se procesa el resto del endpoint",
     */
    @GetMapping("/{category}/{subcategory}/{productType}")
    // Parametros de endpoint:
    /*
     * Un parametro "page" para la paginacion de los productos.
     * Multiparametros para los filtros(PARAMETROS DE FILTRO NORMAL).
     * Un parametro para una rango de precio(PARAMETRO DE FILTRO ESPECIAL 1).
     * Un parametro para ordenar los productos que se van a listar EN FUNCION A RELEVANCIA, MENOR PRECIO, MAYOR PRECIO, ETC(parametros de filtro ESPECIAL 2).
     */
    public String ItemsByProductType(@RequestParam Map<String, Object> params, @RequestParam(name = "page", defaultValue = "0") int page,
            @PathVariable(required = true, name = "category") String category,
            @PathVariable(required = true, name = "subcategory") String subcategory,
            @PathVariable(required = true, name = "productType") String productType, Model model) {
        categoryDao.findByName(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        subCategoryDao.findByName(subcategory)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado."));
        productTypeDao.findByType(productType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso no encontrado"));


        List<String> procedFilters = new ArrayList<String>();
        params.keySet().iterator().forEachRemaining(param -> {
            if (param.matches("filter[0-20]")){
                procedFilters.add(String.join("-", Arrays.asList(params.get(param).toString().split(":"))));
            }
        });

        
        // Creacion de query que liste productos(PAGINADOS Y NO PAGINADOS):
        // Recibe:
        /*
         * Un array con todos los filtros NORMALES detectados.
         * El tipo de productos que en el que estamos.
         */
        var readyQuery = generatedFilterQuery(procedFilters, productType);
        System.out.println(readyQuery);


        // Consulta desde el dao de productos con EntityManager, para listar los productos con o sin filtros, y su respectiva paginacion:
        // Recibe:
        /*
         * El pageRequest con la cantidad maxima de items por pagina
         * El query final para consultar en la base de datos.
         */
        var productItems = productoEntityManager.findProductsWithCharacteristics(PageRequest.of(page, 5), readyQuery);

        model.addAttribute("items", productItems.get("dataPaged"));
        return "items/list";
    }

    // FUNCION PARA GENERAR UN QUERY DE BUSQUEDA DE PRODUCTOS:
    /*
     * Esta funcion se encarga de generar un query que sirva buscar productos de manera paginada, pero en base a cierta cantidad de caracteristicas y a un tipo de producto.
     * Pueden ser 0 o "n" caracteristicas.
     * Los parametros a recibir son las caracteristicas en un formato especifico(Tipo-valor), desde una Array List.
     * Y el segundo parametro es el tipo de producto que se esta buscando.
     */
    public String generatedFilterQuery(List<String> procedFilters, String productType) {

        // Contamos con una serie de partes del query final, que sea la cantidad de caracteristicas que reciba la funcion, estas partes del query siempre van a estar.
        // PARTE 1:
        /*
         * Primera parte del query(preQuery1): Campos a mostrar y seleccion de la tabla de productos.
         * Segunda parte del query(preQuery2): Union con los tipos de productos, para asi poder seleccionar solo a los productos que pertenezcan a un tipo de producto.
         * Tercera parte del query(preQuery2): Parte final, donde se incluye a el campo de la tabla de tipos de productos con el que incluiremos la condicion del tipo de productos que estamos buscando.
         */
        String preQuery1 = "SELECT productos.id, productos.code, productos.description, productos.price, productos.rating, productos.stock, productos.title FROM productos";
        String preQuery2 = " left outer join product_types_asigned on productos.id = product_types_asigned.product_id left outer join product_types on product_types_asigned.product_type_id = product_types.id where";
        String preQuery4 = "and product_types.type = '";

        // PARTE 2:
        /*
         * Primer obj: Se usa para generar un identificador de union de tabla para la tabla characteristic_values_asigned(SE GENERAN EN FUNCION A LA CANTIDAD DE CARACTERISTICAS).
         * Segundo obj: Se usa para generar un identificador de union de tabla para la tabla characteristic_values(SE GENERAN EN FUNCION A LA CANTIDAD DE CARACTERISTICAS).
         */
        String objCVA = "cva";
        String objCV = "cv";

        // Array list que contendra todas las partes del query final(AL HACER EL RETURN DE LA FUINCION, SE HARA UN JOIN AL ARRAY PARA OBTENER UN STRING).
        List<String> queries = new ArrayList<String>();
        // Empezamos por añadir la primera parte del query:
        queries.add(preQuery1);

        // Contadores:
        /*
         * Primer contador: Se usa para ir contanto la cantidad de caracteristicas que se recorreran en los respectivos bucles for, para ir generando los identificadores de union a tablas.
         * Segundo contador: Se usa para ir contanto la cantidad de condiciones a incluir en final del query, para asi poder calcular la ultima condicion y terminar de cerrar el where.
         */
        int cont = 0;
        int acum = 0;

        // Si la lista de caracteristicas esta vacia(NO HAY NINGUNA):
        if (procedFilters.isEmpty()) {
            // Retornamos el query sin ningun filtro:
            String noFilterQuery = "SELECT productos.id, productos.code, productos.description, productos.price, productos.rating, productos.stock, productos.title FROM productos left outer join product_types_asigned on productos.id = product_types_asigned.product_id left outer join product_types on product_types_asigned.product_type_id = product_types.id where product_types.type = '"
                .concat(productType + "'");
            return noFilterQuery;
        }

        // Si la lista de caracteristicas no esta vacia(ITERAMOS POR CADA CARACTERISTICA):

        // PRIMERA ITERACION:
        /*
         * Generacion de uniones para la tabla characteristic_values_asigned.
         */
        for (int i = 0; i < procedFilters.size(); i++) {
            // Aumentamos el contador de caracteristicas(PORQUE EL PRIMER IDENTIFICADOR DEBE CARGARSE CON EL 1):
            cont++;
            // Generamos la parte del query para la union con la tabla "characteritic_values_asigned" en funcion a la caracteristica actual: 
            var query = " left outer join characteristic_values_asigned ".concat(objCVA + String.valueOf(cont))
                    .concat(" on productos.id = " + objCVA + String.valueOf(cont) + ".product_id");
            // Añadimos la parte generada a la lista del query final:
            queries.add(query);
            continue;
        }

        // SEGUNDA ITERACION:
        /*
         * Se reinicia el primer contador, porque se generaran nuevas uniones en funcion a las caracteristicas.
         * Generacion de uniones para la tabla characteristic_values.
         */
        cont = 0;
        for (int i = 0; i < procedFilters.size(); i++) {
            // Aumentamos el contador de caracteristicas(PORQUE EL PRIMER IDENTIFICADOR DEBE CARGARSE CON EL 1):
            cont++;
            // Generamos la parte del query para la union con la tabla "characteritic_values" en funcion a la caracteristica actual: 
            var query = " left outer join characteristic_values ".concat(objCV + String.valueOf(cont))
                    .concat(" on " + objCVA + String.valueOf(cont) + ".characteristic_value_id = " + objCV
                            + String.valueOf(cont) + ".id");
            // Añadimos la parte generada a la lista del query final:
            queries.add(query);
            continue;
        }

        // Añadirmos la parte 2 del query al list del query final:
        queries.add(preQuery2);

        // TERCERA ITERACION:
        /*
         * Se reinicia el primer contador, porque se generaran las condiciones respectivas para la cantidad de caracteristicas mandadas como parametros.
         * Generacion de condiciones para la parte final del query.
         */
        cont = 0;
        for (int i = 0; i < procedFilters.size(); i++) {
            // Aumentamos el contador de caracteristicas(PORQUE EL PRIMER IDENTIFICADOR DEBE CARGARSE CON EL 1):
            cont++;
            // Si el segundo acumulador(QUE CUENTA EN QUE CARACTERISTICA E ITERACION ESTAMOS), es menor que el ultimo elemento de la lista de caracteristicas(DANDO A ENTENDER QUE AUN NO LLEGAMOS A LA ULTIMA CARACTERISTICA):
            if (acum < procedFilters.size() - 1) {
                // CASO 1:
                /*
                 * Generamos un query con un and al final (YA QUE AUN FALTAN ITERAR MAS CARACTERISTICAS).
                 */
                var query = " ".concat(
                        objCV + String.valueOf(cont) + ".description_value = '" + procedFilters.get(i) + "' and");
                // Añadimos esa parte genrada a la lista de query final:
                queries.add(query);
                // Aumentamos la iteracion(SIGUENTE CARACTERISTICA):
                acum++;
            } else {
                // CASO 2:
                /*
                 * Generamos un query que cierre la cantidad de condiciones que hayan, en funcion a la cantidad de caracteristicas.
                 */
                var query = " "
                        .concat(objCV + String.valueOf(cont) + ".description_value = '" + procedFilters.get(i) + "'");
                // Añadimos esa ultima parte al list final del query:
                queries.add(query);
            }
        }
        // Al final de todo, añadimos la ultima parte del query final(LA PARTE QUE EVALUA LA CONDICION PARA EL TIPO DE PRODUCTOS QUE BUSCAMOS):
        queries.add(preQuery4.concat(productType + "'"));
        // Retornamos un join del list(UN STRING):
        return String.join("", queries);
    }
}
