package com.puc.edificad.services;

import com.jnunes.spgcore.model.BaseEntity;
import com.puc.edificad.services.dto.AutocompleteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface PesquisaService {

    default <T extends BaseEntity> List<AutocompleteDto> createFrom(List<T> entities, Function<T, String> getIdentify) {
        final List<AutocompleteDto> dtoList = new ArrayList<>();

        for (T entity : entities) {
            AutocompleteDto dto = new AutocompleteDto();
            dto.setId(entity.getId());
            dto.setText(getIdentify.apply(entity));
            dtoList.add(dto);
        }

        return dtoList;
    }

    List<AutocompleteDto> obterBeneficiarios(String searchValue);

    List<AutocompleteDto> obterCestas(String searchValue);

    List<AutocompleteDto> obterVoluntarios(String searchValue);

    List<AutocompleteDto> obterUserRoles(String search);
}
