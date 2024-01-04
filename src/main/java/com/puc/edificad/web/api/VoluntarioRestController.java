package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voluntario")
public class VoluntarioRestController extends BaseController {

    private VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<Voluntario> list(@RequestParam(required = false) Long id, @RequestParam(required = false) String cpf,
        @RequestParam(required = false) String nome) {
        return service.findByNome(nome);
    }

    @GetMapping("/{id}")
    public Voluntario findById(@PathVariable Long id){
        return service.findById(id).orElseThrow(EntityNotFoundException::notFoundForId);
    }

    @PostMapping
    public Voluntario create(@RequestBody Voluntario voluntario) {
        service.save(voluntario);
        return voluntario;
    }

    @PostMapping("/create-from-list")
    public List<Voluntario> create(@RequestBody List<Voluntario> voluntarios) {
        voluntarios.forEach(service::save);
        return voluntarios;
    }

    @PutMapping
    public Voluntario update(@RequestBody Voluntario voluntario) {
        ValidationUtils.validateNonNull(voluntario::getId, "entity.id.not.null");
        return service.update(voluntario);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Voluntario> entity = service.findById(id);
        entity.map(Voluntario::getId).ifPresent(service::deleteById);

        return entity.map(Voluntario::getNome).map(nome -> msg.get("voluntario.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
