package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.DependenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dependente")
public class DependenteController extends BaseController{

    private DependenteService service;

    @Autowired
    public void setService(DependenteService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<Dependente> listAll() {
        return service.findAll();
    }

    @PostMapping
    public Dependente create(@RequestBody Dependente entity) {
        service.save(entity);
        return entity;
    }

    @PutMapping
    public void update(@RequestBody Dependente entity){
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        service.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        Optional<Dependente> entity = service.findById(id);
        entity.map(BaseEntity::getId).ifPresent(service::deleteById);

        return entity.map(Dependente::getNome).map(nome -> msg.get("dependente.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
