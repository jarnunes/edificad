package com.puc.edificad.services;

import com.jnunes.spgcore.services.AutocompleteService;
import com.jnunes.spgcore.services.dto.AutocompleteDto;

import java.util.List;

public interface PesquisaService extends AutocompleteService {


    List<AutocompleteDto> obterBeneficiarios(String searchValue);

    List<AutocompleteDto> obterCestas(String searchValue);

    List<AutocompleteDto> obterVoluntarios(String searchValue);

}
