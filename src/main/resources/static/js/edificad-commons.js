
buildDependentes = function (dependentes) {
    if(dependentes.length === 0)
        return '';

    let tr = dependentes.map(dep => createDepRow(dep)).join('');
    return `<table class="table table-sm caption-top table-bordered table-striped" >
                    <caption class="text-center fw-semibold">Dependentes</caption>
                    <thead>
                        <tr>
                            <th th:text="#{entity.id}">ID</th>
                            <th th:text="#{pessoa.nome}">Nome</th>
                            <th th:text="#{pessoa.cpf}">CPF</th>
                            <th th:text="#{pessoa.data.nascimento}">Data Nascimento</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tr}
                    </tbody>
                 </table>`;
}

createDepRow = function (dep) {
    return `<tr>
            <td>${dep.id}</td>
            <td>${dep.nome}</td>
            <td>${dep.cpf}</td>
            <td>${dep.dataNascimento}</td>
            </tr>`;
}

buildEndereco = function (endereco) {
    return `<dl>
                    <dt>Endereço: </dt>
                    <dd>${endereco.logradouro}, nº ${endereco.numero} - ${endereco.bairro}</dd>
                    <dd>${endereco.cep} / ${endereco.cidade}</dd>
                </dl>`;
}
