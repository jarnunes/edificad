package com.puc.edificad.builder;

import com.puc.edificad.model.Cesta;

public class CestaBuilder {

    private final Cesta cesta;

    public CestaBuilder() {
        cesta = new Cesta();
    }

    public CestaBuilder comNome(String nome) {
        cesta.setNome(nome);
        return this;
    }

    public CestaBuilder comDescricao(String descricao) {
        cesta.setDescricao(descricao);
        return this;
    }

    public CestaBuilder comQuantidadeEstoque(Integer qtdeEstoque) {
        cesta.setQuantidadeEstoque(qtdeEstoque);
        return this;
    }

    public Cesta build() {
        return cesta;
    }
}
