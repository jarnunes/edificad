package com.puc.edificad.web.api;

import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioRestController extends BaseController {

    private DistribuicaoCestaService service;

    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @GetMapping("/resumo-distribuicao")
    ResumoDistribuicaoCestaDto obterResumo() {
        return service.obterResumoDeDistribuicaoCestas();
    }

    @GetMapping("/resumo-beneficiarios-assistidos")
    List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidos(
        @RequestParam Integer anoReferencia, @RequestParam Integer mesReferencia,
        @RequestParam Long qtdMesesAnteriores) {

        return service.obterQuantidadesBeneficiariosAssistidosPorMesAno(DateTimeUtils.toYear(anoReferencia),
            DateTimeUtils.toMonth(mesReferencia), qtdMesesAnteriores);
    }

    @GetMapping("/resumo-cestas-distribuidas")
    List<QuantidadesPorAnoMes> obterQuantidadesCestasDistribuidas(
        @RequestParam Integer anoReferencia, @RequestParam Integer mesReferencia,
        @RequestParam Long qtdMesesAnteriores) {

        return service.obterQuantidadeCestasDistribuidasPorMesAno(DateTimeUtils.toYear(anoReferencia),
            DateTimeUtils.toMonth(mesReferencia), qtdMesesAnteriores);
    }

}
