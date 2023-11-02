package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.web.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/cesta")
public class CestaController extends BaseController {

    private CestaService service;

    @Autowired
    public void setService(CestaService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<Cesta> list(@RequestParam(required = false) Long id, @RequestParam(required = false) String nome) {
        return service.findByIdNome(id, nome);
    }

    @PostMapping
    public Cesta create(@RequestBody Cesta cesta){
        return service.save(cesta);
    }

    @PutMapping
    public Cesta update(@RequestBody Cesta entity){
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Cesta> entity = service.findById(id);
        entity.map(BaseEntity::getId).ifPresent(service::deleteById);

        return entity.map(Cesta::getNome).map(nome -> msg.get("cesta.success.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
