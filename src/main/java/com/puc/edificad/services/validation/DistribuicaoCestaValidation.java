package com.puc.edificad.services.validation;

import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.CestaServiceImpl;
import com.puc.edificad.services.config.ParametroService;
import com.puc.edificad.services.config.ParametroServiceImpl;
import org.apache.commons.lang3.StringUtils;

public class DistribuicaoCestaValidation {

    final DistribuicaoCesta entity;
    final CestaService cestaService;
    final ParametroService parametroService;

    public DistribuicaoCestaValidation(DistribuicaoCesta entityIn){
        this.entity = entityIn;
        this.cestaService = StaticContextAccessor.getBean(CestaServiceImpl.class);
        this.parametroService = StaticContextAccessor.getBean(ParametroServiceImpl.class);
    }

    public static DistribuicaoCestaValidation getInstance(DistribuicaoCesta entityIn){
        return new DistribuicaoCestaValidation(entityIn);
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

    public void validarSePermiteCancelarDistribuicao(){
        ValidationUtils.validate(entity.getCancelamento() == null
            || parametroService.permitirCancelamentoDistribuicaoCesta(),
            "distribuicao.cesta.cancelamento.nao.permitido");
    }

    public void validarSeExisteJustificativaCancelamento(){
        ValidationUtils.validate(entity.getCancelamento() == null
            || parametroService.permitirCancelamentoDistribuicaoCesta()
            && StringUtils.isNotEmpty(entity.getMotivoCancelamento()),
            "distribuicao.cesta.motivo.cancelamento.obrigatorio");
    }

}
