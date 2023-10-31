package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/distribuicao-cesta")
public class DistribuicaoCestaController extends BaseController{

    private DistribuicaoCestaService service;

    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping
    public List<DistribuicaoCestaDto> listAll() {
        return service.findAllDto();
    }

    @PostMapping
    public DistribuicaoCestaDto create(@RequestBody DistribuicaoCestaDto dto) {
        service.save(dto);
        return dto;
    }

    @PutMapping
    public void update(@RequestBody DistribuicaoCestaDto entity){
        service.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        Optional<DistribuicaoCesta> entity = service.findById(id);
        entity.map(BaseEntity::getId).ifPresent(service::deleteById);

        return entity.map(DistribuicaoCesta::getId).map(nome -> msg.get("distribuicao.cesta.remove", nome))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }
}
