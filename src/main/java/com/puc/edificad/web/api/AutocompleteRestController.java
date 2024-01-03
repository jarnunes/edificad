package com.puc.edificad.web.api;

import com.puc.edificad.model.State;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.services.dto.AutocompleteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteRestController {

    @Autowired
    private PesquisaService pesquisaService;

    @GetMapping("/produtos")
    public List<AutocompleteDto> completeProdutos(@RequestParam(value = "q", required = false) String query) {
       return  IntStream.range(0, 10).mapToObj(i -> {
            AutocompleteDto dto = new AutocompleteDto();
            dto.setId(i);
            dto.setSlug(i + "_slug");
            dto.setText(i + "_text");
            return dto;
        }).toList();
    }

    @GetMapping("/fornecedores")
    public List<AutocompleteDto> completeFornecedores(@RequestParam(value = "q", required = false) String query) {
        return  IntStream.range(0, 10).mapToObj(i -> {
            AutocompleteDto dto = new AutocompleteDto();
            dto.setId(i);
            dto.setSlug(i + "_fornecedor_slug");
            dto.setText(i + "_fornecedor_text");
            return dto;
        }).toList();
    }


    @GetMapping("/beneficiarios")
    public List<AutocompleteDto> completeBeneficiarios(@RequestParam(value = "q", required = false) String query) {
        return completeStates(null);
    }

    @GetMapping("/states")
    public List<AutocompleteDto> completeStates(@RequestParam(value = "q", required = false) String query) {
        List<AutocompleteDto> autcomplete = Arrays.stream(State.values()).map(state -> {
            AutocompleteDto autocompleteDto = new AutocompleteDto();
            autocompleteDto.setId(state);
            autocompleteDto.setText(state.getLabel());
            autocompleteDto.setSlug(state.name());
            return autocompleteDto;
        }).toList();


        autcomplete = completeBeneficiarios(null);
        return autcomplete;
    }

}
