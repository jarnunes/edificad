package com.puc.edificad.web.api.configuracao;


import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.config.Configuracao;
import com.puc.edificad.services.config.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfiguracaoRestController {

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
        return configuracaoService.save(entity);
    }

    @PutMapping
    public Configuracao update(@RequestBody Configuracao entity) {
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        return configuracaoService.update(entity);
    }

}
