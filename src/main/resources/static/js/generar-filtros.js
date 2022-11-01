(() => {

    const filtros = ["Color:negro", "Color:rojo", "Marca:HP"];

    const btnGenerarFiltros = document.querySelector('#btnGenerarFiltros');
    btnGenerarFiltros.addEventListener('click', () => {
        generarUrl(filtros);
    })

    const generarUrl = (filtros) => {
        const nameParams = [];
        for(let i = 0; i < filtros.length; i++) {
            console.log(filtros.length)
            nameParams.push("filter".concat(i));
        }
        const urlParams = new URLSearchParams(window.location.search);
        for(let i = 0; i < nameParams.length; i++) {
            console.log(nameParams[i])
            urlParams.set(nameParams[i], filtros[i]);
        }
        window.location.search = urlParams;
    }

})();