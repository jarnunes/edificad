package com.puc.edificad.web.api;

import com.jnunes.spgcore.service.dto.AutocompleteDto;
import com.puc.edificad.services.PesquisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteRestController {

    private PesquisaService pesquisaService;

    @Autowired
    public void setPesquisaService(PesquisaService pesquisaServiceIn) {
        this.pesquisaService = pesquisaServiceIn;
    }

    @GetMapping("/beneficiarios")
    public List<AutocompleteDto> completeBeneficiarios(@RequestParam(value = "q", required = false) String query) {
        return pesquisaService.obterBeneficiarios(query);
    }

    @GetMapping("/cestas")
    public List<AutocompleteDto> completeCestas(@RequestParam(value = "q", required = false) String query) {
        return pesquisaService.obterCestas(query);
    }

    @GetMapping("/voluntarios")
    public List<AutocompleteDto> completeVoluntarios(@RequestParam(value = "q", required = false) String query) {
        return pesquisaService.obterVoluntarios(query);
    }

}
