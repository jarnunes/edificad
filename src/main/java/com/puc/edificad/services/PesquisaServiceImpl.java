package com.puc.edificad.services;

import com.puc.edificad.services.dto.AutocompleteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PesquisaServiceImpl implements PesquisaService {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Override
    public List<AutocompleteDto> obterBeneficiarios(String searchValue) {
        return beneficiarioService.findAll().stream().map(beneficiario -> {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setId(beneficiario);
            autocompleteDto.setText(beneficiario.getNome());
            autocompleteDto.setSlug(beneficiario.getNome());
            return autocompleteDto;
        }).toList();
    }
}
