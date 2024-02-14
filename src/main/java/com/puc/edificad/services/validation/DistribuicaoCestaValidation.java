package com.puc.edificad.services.validation;

import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.CestaServiceImpl;

public class DistribuicaoCestaValidation {

    final DistribuicaoCesta entity;
    final CestaService cestaService;

    public DistribuicaoCestaValidation(DistribuicaoCesta entityIn){
        this.entity = entityIn;
        this.cestaService = StaticContextAccessor.getBean(CestaServiceImpl.class);
    }

    public void validarQuantidadeEstoque() {
        final Integer qtdeEstoque = cestaService.obterQuantidadeEstoque(entity.getCesta().getId());
        ValidationUtils.validate(qtdeEstoque > 1,
            "distribuicao.cesta.quantidade.estoque.inferior.quantidade.distribuida",
                entity.getCesta().getNome(), qtdeEstoque);
    }

    public void validarSeDataHoraEntregaSuperiorDataHoraAtual(){
        ValidationUtils.validateIsAfterNow(entity.getDataHora(), "distribuicao.cesta.data.hora.registro.invalido",
            DateTimeUtils.formatter(entity.getDataHora()));
    }

}
