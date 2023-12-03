package com.puc.edificad.commons.builder;

import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.Estado;

public class EnderecoBuilder {

    private final Endereco endereco;

    public EnderecoBuilder() {
        endereco = new Endereco();
    }

    public EnderecoBuilder comLogradouro(String logradouro) {
        endereco.setLogradouro(logradouro);
        return this;
    }

    public EnderecoBuilder comNumero(String numero) {
        endereco.setNumero(numero);
        return this;
    }

    public EnderecoBuilder comCep(String cep) {
        endereco.setCep(cep);
        return this;
    }

    public EnderecoBuilder comBairro(String bairro) {
        endereco.setBairro(bairro);
        return this;
    }

    public EnderecoBuilder comCidade(String cidade) {
        endereco.setCidade(cidade);
        return this;
    }

    public EnderecoBuilder comEstado(Estado estado) {
        endereco.setEstado(estado);
        return this;
    }

    public Endereco build() {
        return endereco;
    }
}
