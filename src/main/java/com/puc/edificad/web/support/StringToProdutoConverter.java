package com.puc.edificad.web.support;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProdutoConverter implements Converter<String, Produto> {

    @Override
    public Produto convert(String id) {
        // Lógica para converter a String para um Produto
        Produto produto = new Produto();
        produto.setId(Long.parseLong(id)); // Supondo que o ID seja um Long

        // Você pode implementar a lógica para buscar o produto no banco de dados usando o ID, caso necessário

        return produto;
    }
}