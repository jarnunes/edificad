package com.puc.edificad.web.controller.dashboard;

import com.puc.edificad.commons.utils.ChartUtils;
import com.puc.edificad.commons.utils.DateTimeUtils;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.dto.CardDto;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;
import com.puc.edificad.web.controller.dashboard.dto.ChartValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private DistribuicaoCestaService service;
    private CestaService cestaService;

    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    public void setCestaService(CestaService serviceIn) {
        this.cestaService = serviceIn;
    }

    @GetMapping
    String view(ModelMap model) {
        model.addAttribute("cards", createCards());
        model.addAttribute("chartCestasEntregues", criarChartCestasEntregues());
        model.addAttribute("chartBeneficiariosAssistidos", criarChartBeneficiariosAssistidos());
        model.addAttribute("chartCestasEmEstoque", criarChartQuantidadesCestasPorNome());
        return "dashboard/view";
    }

    private String criarChartCestasEntregues() {
        List<QuantidadesPorAnoMes> quantidades =
            service.obterQuantidadeCestasDistribuidasPorMesAno(Year.now(), LocalDate.now().getMonth(), 6L);

        ChartValues chartValues = new ChartValues();
        chartValues.setBackgroundColors(ChartUtils.getPaletaCoresRGBA());
        chartValues.setBorderColors(ChartUtils.getPaletaCoresBarChartRGB());

        for (QuantidadesPorAnoMes quantidade : quantidades) {
            chartValues.addData(quantidade.getQuantidade().intValue());
            chartValues.addLabel(DateTimeUtils.getShortName(quantidade.getMes()));
        }

        return JsonUtils.toJsonString(chartValues);
    }

    private String criarChartBeneficiariosAssistidos() {
        List<QuantidadesPorAnoMes> quantidades =
            service.obterQuantidadesBeneficiariosAssistidosPorMesAno(Year.now(), LocalDate.now().getMonth(), 6L);

        ChartValues chartValues = new ChartValues();
        for (QuantidadesPorAnoMes quantidade : quantidades) {
            chartValues.addData(quantidade.getQuantidade().intValue());
            chartValues.addLabel(DateTimeUtils.getShortName(quantidade.getMes()));
        }

        return JsonUtils.toJsonString(chartValues);
    }

    private String criarChartQuantidadesCestasPorNome() {
        List<Cesta> cestas = cestaService.findAll();

        ChartValues chartValues = new ChartValues();
        chartValues.setBackgroundColors(ChartUtils.getPaletaCoresPieChartRGBA());
        chartValues.setBorderColors(ChartUtils.getPaletaCoresPieChartRGB());

        for (Cesta cesta : cestas) {
            chartValues.addData(cesta.getQuantidadeEstoque());
            chartValues.addLabel(cesta.getNome());
        }

        return JsonUtils.toJsonString(chartValues);
    }

    private List<CardDto> createCards() {
        List<CardDto> cardsDto = new ArrayList<>();
        ResumoDistribuicaoCestaDto resumo = service.obterResumoDeDistribuicaoCestas();
        cardsDto.add(criarCardQuantidadeDistribuida(resumo));
        cardsDto.add(criarCardQuantidadeBeneficiariosAssistidos(resumo));
        cardsDto.add(criarCardQuantidadeEstoque());
        return cardsDto;
    }

    private CardDto criarCardQuantidadeEstoque() {
        CardDto card = new CardDto();
        card.setTitle("Cestas em Estoque");
        card.setClassColor("card-cian");
        card.setIcon("bi-database-exclamation");
        card.setCount(cestaService.findAll().stream().map(Cesta::getQuantidadeEstoque).reduce(Integer::sum).orElse(0));
        return card;
    }

    private CardDto criarCardQuantidadeDistribuida(ResumoDistribuicaoCestaDto resumo) {
        CardDto card = new CardDto();
        card.setTitle("Quantidade Distribuída");
        card.setClassColor("card-green");
        card.setIcon("bi-basket2");
        card.setCount(resumo.getCestasDistribuidas());
        return card;
    }

    private CardDto criarCardQuantidadeBeneficiariosAssistidos(ResumoDistribuicaoCestaDto resumo) {
        CardDto card = new CardDto();
        card.setTitle("Beneficiários Assistidos");
        card.setClassColor("card-red");
        card.setIcon("bi-person-hearts");
        card.setCount(resumo.getBeneficiariosAssistidos());
        return card;
    }

}
