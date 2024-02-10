package com.puc.edificad.web.controller;

import com.jnunes.spgcore.service.dto.AutocompleteDto;
import com.puc.edificad.services.PesquisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/autocomplete")
public class AutocompleteController {

    private PesquisaService pesquisaService;

    @Autowired
    public void setPesquisaService(PesquisaService pesquisaServiceIn) {
        this.pesquisaService = pesquisaServiceIn;
    }

    @GetMapping("/voluntarios")
    public ResponseEntity<List<AutocompleteDto>> completeVoluntarios(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(pesquisaService.obterVoluntarios(query));
    }


    @GetMapping("/beneficiarios")
    public ResponseEntity<List<AutocompleteDto>> completeBeneficiarios(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(pesquisaService.obterBeneficiarios(query));
    }


    @GetMapping("/cestas")
    public ResponseEntity<List<AutocompleteDto>> completeCestas(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(pesquisaService.obterCestas(query));
    }
}
