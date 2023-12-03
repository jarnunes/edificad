package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiario")
public class BeneficiarioRestController extends BaseController {

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

    @GetMapping("/{id}")
    Beneficiario findById(@PathVariable Long id){
        return service.findById(id).orElseThrow(EntityNotFoundException::notFoundForId);
    }

    @PostMapping
    Beneficiario create(@RequestBody Beneficiario beneficiario) {
        service.save(beneficiario); // Usar um DTO.
        return beneficiario;
    }

    @PostMapping("/create-from-list")
    List<Beneficiario> createFromList(@RequestBody List<Beneficiario> beneficiarios){
        beneficiarios.forEach(service::save);
        return beneficiarios;
    }

    @PutMapping
    Beneficiario update(@RequestBody BeneficiarioDto entity) {
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable Long id) {
        Optional<Beneficiario> beneficiario = service.findById(id);
        beneficiario.map(Beneficiario::getId).ifPresent(service::deleteById);

        return beneficiario.map(Beneficiario::getNome).map(nome -> msg.get("beneficiario.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }

}
