package com.puc.edificad.model.validator;

import com.puc.edificad.model.Beneficiario;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BeneficiarioValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Beneficiario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Beneficiario entity = (Beneficiario) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "notEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "notEmpty");
    }
}
