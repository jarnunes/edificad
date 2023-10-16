package com.puc.edificad.web.api.configuracao;


import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.Configuracao;
import com.puc.edificad.services.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
//@PreAuthorize("hasRole('ADMIN')")
public class ConfiguracaoController {

    private ConfiguracaoService configuracaoService;

    @Autowired
    public void setConfiguracaoService(ConfiguracaoService serviceIn) {
        this.configuracaoService = serviceIn;
    }

    @GetMapping
    public Configuracao read() {
        return configuracaoService.findFirstConfiguracao().orElseThrow(EntityNotFoundException::notFound);
    }

    @PostMapping
    public Configuracao create(@RequestBody Configuracao entity) {
        configuracaoService.save(entity);
        return entity;
    }

    @PutMapping
    public Configuracao update(@RequestBody Configuracao entity) {
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        configuracaoService.update(entity);
        return entity;
    }

}
