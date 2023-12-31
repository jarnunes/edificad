package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.jnunes.core.commons.utils.ValidationUtils;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;
import com.puc.edificad.services.DependenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dependente")
public class DependenteRestController extends BaseController{

    private DependenteService service;

    @Autowired
    public void setService(DependenteService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<DependenteDto> listAll() {
        return service.findAllDto();
    }

    @PostMapping
    public DependenteDto create(@RequestBody DependenteDto dto) {
        return service.save(dto);
    }

    @PostMapping("/create-from-list")
    public List<DependenteDto> create(@RequestBody List<DependenteDto> dependenteDtos) {
        return dependenteDtos.stream().map(service::save).toList();
    }

    @PutMapping
    public void update(@RequestBody DependenteDto entity){
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        service.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        Optional<Dependente> entity = service.findById(id);
        entity.map(Dependente::getId).ifPresent(service::deleteById);

        return entity.map(Dependente::getNome).map(nome -> msg.get("dependente.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
