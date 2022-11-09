// Archivo JS para generar una url con parametros GET encargados de leer los filtros de busqueda para los productos.
(() => {

    // Ejm de fitlros(LA NOMENCLATURA DEBE SER SIEMPRE TIPO:VALOR)
    const filtros = ["Color:negro", "Color:rojo", "Marca:HP"];

    // Leemos el boton que genera los filtros y creamos un evento a partir de este:
    const btnGenerarFiltros = document.querySelector('#btnGenerarFiltros');
    btnGenerarFiltros.addEventListener('click', () => {
        generarUrl(filtros);
    })

    // Funcion flecha encargada de genera la url, y que al final redireccionara a esa url generada.
    const generarUrl = (filtros) => {
        const urlParams = new URLSearchParams(window.location.search);
        for(let i = 0; i < filtros.length; i++) {
            urlParams.set("filter".concat(i), filtros[i]);
        }
        window.location.search = urlParams;
    }

})();