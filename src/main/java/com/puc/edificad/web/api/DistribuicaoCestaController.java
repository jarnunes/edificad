package com.puc.edificad.web.api;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.services.DistribuicaoCestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<DistribuicaoCestaDto> findBy(@RequestParam(required = false) String cesta,
        @RequestParam(required = false) String cpfBeneficiario, @RequestParam(required = false) String cpfVoluntario,
        @RequestParam(required = false) LocalDate data) {
        return service.findBy(cesta, cpfBeneficiario, cpfVoluntario, data);
    }

    @PostMapping
    public DistribuicaoCestaDto create(@RequestBody DistribuicaoCestaDto dto) {
        return service.save(dto);
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
