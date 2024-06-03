package com.puc.edificad.web.controller;

import com.jnunes.spgauth.model.config.AuthEmailParameterType;
import com.jnunes.spgcore.commons.LabelMessage;
import com.jnunes.spgcore.services.AutocompleteService;
import com.jnunes.spgcore.services.dto.AutocompleteDto;
import com.puc.edificad.model.config.TipoDominioParametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.services.PesquisaService;
import org.codehaus.groovy.transform.sc.transformers.RangeExpressionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping("/parametros")
    public ResponseEntity<List<AutocompleteDto>> completeParametros(@RequestParam(value = "q", required = false) String query) {
        List<AutocompleteDto> parametros = new ArrayList<>();
        parametros.addAll(pesquisaService.createFrom(TipoParametroConfiguracao.values(), query));
        parametros.addAll(pesquisaService.createFrom(AuthEmailParameterType.values(), query));
        return ResponseEntity.ok(parametros);
    }

    @GetMapping("/dominio-parametros")
    public ResponseEntity<List<AutocompleteDto>> completeDominios(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(pesquisaService.createFrom(TipoDominioParametro.values(), query));
    }


}
