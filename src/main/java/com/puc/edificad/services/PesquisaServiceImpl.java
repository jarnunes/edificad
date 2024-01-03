package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Voluntario;
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

    @Autowired
    private VoluntarioService voluntarioService;
    @Autowired
    private CestaService cestaService;

    @Override
    public List<AutocompleteDto> obterBeneficiarios(String searchValue) {
        return criarDe(beneficiarioService.findAll(), Beneficiario::getNome);
    }

    @Override
    public List<AutocompleteDto> obterCestas(String searchValue) {
        return criarDe(cestaService.findAll(), Cesta::getNome);
    }

    @Override
    public List<AutocompleteDto> obterVoluntarios(String searchValue) {
        return criarDe(voluntarioService.findAll(), Voluntario::getNome);
    }

}
