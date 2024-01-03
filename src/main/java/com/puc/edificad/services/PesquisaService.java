package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.dto.AutocompleteDto;

import java.util.List;

public interface PesquisaService {


    List<AutocompleteDto> obterBeneficiarios(String searchValue);
}
