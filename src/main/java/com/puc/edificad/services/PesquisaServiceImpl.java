package com.puc.edificad.services;

import com.jnunes.spgauth.model.Role;
import com.jnunes.spgcore.service.dto.AutocompleteDto;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PesquisaServiceImpl implements PesquisaService {

    private BeneficiarioService beneficiarioService;
    private VoluntarioService voluntarioService;
    private CestaService cestaService;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn){
        this.beneficiarioService = serviceIn;
    }

    @Autowired
    public void setVoluntarioService(VoluntarioService serviceIn){
        this.voluntarioService = serviceIn;
    }

    @Autowired
    public void setCestaService(CestaService serviceIn){
        this.cestaService = serviceIn;
    }

    @Override
    public List<AutocompleteDto> obterBeneficiarios(String searchValue) {
        return createFrom(beneficiarioService.findByNomeCpf(searchValue, null), Beneficiario::getNome);
    }

    @Override
    public List<AutocompleteDto> obterCestas(String searchValue) {
        return createFrom(cestaService.findByNome(searchValue), Cesta::getNome);
    }

    @Override
    public List<AutocompleteDto> obterVoluntarios(String searchValue) {
        return createFrom(voluntarioService.findByNome(searchValue), Voluntario::getNome);
    }

}
