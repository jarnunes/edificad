package com.puc.edificad.web.support;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class FornecedorConverter implements Converter<String, Fornecedor> {


    @Override
    public Fornecedor convert(String id) {
        log.error(id);

        if (id == null) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome("tilapia");
            fornecedor.setId(22L);
            return fornecedor;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("tilapia33");
        fornecedor.setId(333L);
        return fornecedor;
    }
}