package com.puc.edificad.web.api;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/beneficiario")
public class BeneficiarioController extends BaseController {

    private BeneficiarioService service;

    @Autowired
    public void setService(BeneficiarioService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    List<Beneficiario> obterBeneficiarios() {
        prePopularBanco();
        return service.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Beneficiario> obterPeloId(@PathVariable Long id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping
    Beneficiario criarBeneficiario(@RequestBody Beneficiario beneficiario) {
        service.save(beneficiario);
        return beneficiario;
    }

    @PutMapping
    Beneficiario atualizar(@RequestBody Beneficiario beneficiario) {
        service.update(beneficiario);
        return beneficiario;
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Beneficiario> remover(@PathVariable Long id) {
        Optional<Beneficiario> beneficiario = service.findById(id);
        beneficiario.map(Beneficiario::getId).ifPresent(service::deleteById);
        return ResponseEntity.of(beneficiario);
    }

    // TODO: apenas para fins de teste. Remover posteriormente.
    private void prePopularBanco() {
        if (service.findAll().isEmpty()) {
            gerarBeneficiarios().forEach(service::save);
        }
    }

    private List<Beneficiario> gerarBeneficiarios() {
        List<Beneficiario> beneficiarios = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            Beneficiario beneficiario = new Beneficiario();
            beneficiario.setCpf("030.302.789-0" + i);
            beneficiario.setNome("BENEFICIARIO_" + i);
            beneficiario.setEmail("beneficiario_" + i + "@gmail.com");
            beneficiario.setTelefone("(31) 99229-135" + i);
            beneficiarios.add(beneficiario);
        });

        return beneficiarios;
    }
}
