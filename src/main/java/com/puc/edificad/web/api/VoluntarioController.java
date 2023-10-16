package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.VoluntarioService;
import com.puc.edificad.web.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/voluntario")
public class VoluntarioController extends BaseController{

    private VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<Voluntario> listAll() {
        return service.findAll();
    }

    @PostMapping
    public Voluntario create(@RequestBody Voluntario voluntario) {
        service.save(voluntario);
        return voluntario;
    }

    @PutMapping
    public void update(@RequestBody Voluntario voluntario){
        ValidationUtils.validateNonNull(voluntario::getId, "entity.id.not.null");
        service.update(voluntario);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        Optional<Voluntario> entity = service.findById(id);
        entity.map(BaseEntity::getId).ifPresent(service::deleteById);

        return entity.map(Voluntario::getNome).map(nome -> msg.get("voluntario.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
