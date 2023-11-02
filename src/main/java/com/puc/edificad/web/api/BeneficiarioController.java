package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<Beneficiario> list(@RequestParam(required = false) Long id,@RequestParam(required = false) String nome,
        @RequestParam(required = false) String cpf) {
        return service.findByIdNomeCpf(id, nome, cpf);
    }

    @PostMapping
    Beneficiario create(@RequestBody Beneficiario beneficiario) {
        service.save(beneficiario);
        return beneficiario;
    }

    @PutMapping
    Beneficiario update(@RequestBody Beneficiario entity) {
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        service.update(entity);
        return entity;
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable Long id) {
        Optional<Beneficiario> beneficiario = service.findById(id);
        beneficiario.map(Beneficiario::getId).ifPresent(service::deleteById);

        return beneficiario.map(Beneficiario::getNome).map(nome -> msg.get("beneficiario.sucess.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }

}
