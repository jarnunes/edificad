$('#datatableCesta').DataTable(configureDatatable({
    endpoint: '/cesta',
    columns: [
        {data: "id"},
        {data: "nome"},
        {data: "quantidadeEstoque"},
        {data: "descricao"}
    ]
}))

setSelectHandler('#datatableCesta');

