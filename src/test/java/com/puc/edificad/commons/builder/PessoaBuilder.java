package com.puc.edificad.commons.builder;

import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.Pessoa;

import java.time.LocalDate;

public abstract class PessoaBuilder<T extends Pessoa> {

    protected final T pessoa;

    protected PessoaBuilder(T especializacaoPessoa) {
        pessoa = especializacaoPessoa;
    }

    public PessoaBuilder<T> comNome(String nome) {
        pessoa.setNome(nome);
        return this;
    }

    public PessoaBuilder<T> comEmail(String email) {
        pessoa.setEmail(email);
        return this;
    }

    public PessoaBuilder<T> comCPF(String cpf) {
        pessoa.setCpf(cpf);
        return this;
    }

    public PessoaBuilder<T> comTelefone(String telefone) {
        pessoa.setTelefone(telefone);
        return this;
    }

    public PessoaBuilder<T> comDataNascimento(LocalDate dataNascimento) {
        pessoa.setDataNascimento(dataNascimento);
        return this;
    }

    public PessoaBuilder<T> comEndereco(Endereco endereco) {
        pessoa.setEndereco(endereco);
        return this;
    }

    public T build() {
        return pessoa;
    }

}
