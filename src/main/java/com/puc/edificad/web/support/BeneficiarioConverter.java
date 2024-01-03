package com.puc.edificad.web.support;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BeneficiarioConverter implements Converter<Object, Beneficiario> {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Override
    public Beneficiario convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Beneficiario beneficiario) {
            return beneficiario;
        } else if (source instanceof Long id) {
            return null;
        }

        return null;
    }


}
