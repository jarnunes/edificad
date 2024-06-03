package com.puc.edificad.web.controller.dashboard;

import com.jnunes.spgauth.commons.utils.AuthUtils;
import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.jnunes.spgparameter.services.ParameterValueService;
import com.puc.edificad.commons.utils.ChartUtils;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.dto.ConfiguracaoDashboardDto;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.config.ParametroService;
import com.puc.edificad.services.dto.CardDto;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;
import com.puc.edificad.web.controller.dashboard.dto.ChartValues;
import com.puc.edificad.web.support.cache.Cache;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private static final String KEY_DASHBOARD_CONFIG = "DASHBOARD_CONFIG";
    private DistribuicaoCestaService service;
    private CestaService cestaService;
    private ParametroService parametroService;
    private Cache cache;

    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    public void setCestaService(CestaService serviceIn) {
        this.cestaService = serviceIn;
    }

    @Autowired
    public void setParametroService(ParametroService parametroServiceIn) {
        this.parametroService = parametroServiceIn;
    }

    @Autowired
    public void setCache(Cache cacheIn) {
        this.cache = cacheIn;
    }

    @GetMapping
    String view(ModelMap model) {
        model.addAttribute("cards", criarCardsResumoDistribuicaoCesta());
        model.addAttribute("chartCestasEntregues", obterDistribuicaoCestasPorMes());
        model.addAttribute("chartBeneficiariosAssistidos", criarChartBeneficiariosAssistidos());
        model.addAttribute("chartCestasEmEstoque", criarChartQuantidadesCestasPorNome());
        return "dashboard/view";
    }

    private ConfiguracaoDashboardDto obterConfiguracaoNoCache(){
        Object config = cache.getItem(getClass(), KEY_DASHBOARD_CONFIG, AuthUtils.currentUsernameRequired());
        return Optional.ofNullable(config).map(ConfiguracaoDashboardDto.class::cast)
                .orElseGet(parametroService::obterConfiguracaoDashboard);
    }

    private String obterDistribuicaoCestasPorMes() {
        List<QuantidadesPorAnoMes> quantidades =
            obterQuantidadesPorMesAno(service::obterQuantidadeCestasDistribuidasPorPeriodo);

        ChartValues chartValues = new ChartValues();
        chartValues.setBackgroundColors(ChartUtils.getPaletaCoresRGBA());
        chartValues.setBorderColors(ChartUtils.getPaletaCoresBarChartRGB());
        adicionarDataLabelToChart(chartValues, quantidades);

        return JsonUtils.toJsonString(chartValues);
    }

    private String criarChartBeneficiariosAssistidos() {
         List<QuantidadesPorAnoMes> quantidades =
            obterQuantidadesPorMesAno(service::obterQuantidadesBeneficiariosAssistidosPorMesAno);

        ChartValues chartValues = new ChartValues();
        adicionarDataLabelToChart(chartValues, quantidades);

        return JsonUtils.toJsonString(chartValues);
    }

    private List<QuantidadesPorAnoMes> obterQuantidadesPorMesAno(
        BiFunction<LocalDateTime, LocalDateTime, List<QuantidadesPorAnoMes>> functionObterQuantidades) {
        final int periodoExibicaoEmMeses = obterConfiguracaoNoCache().getPeriodoExibicaoEmMeses();
        LocalDateTime dataFim = DateTimeUtils.endOfMonth();
        LocalDateTime dataInicio = DateTimeUtils.beginOfMonth().minusMonths(periodoExibicaoEmMeses);
        List<QuantidadesPorAnoMes> quantidades = functionObterQuantidades.apply(dataInicio, dataFim);
        List<YearMonth> mesesExibicao = DateTimeUtils.toPreviousYearMonthList(dataFim, periodoExibicaoEmMeses);

        complementarQuantidadesComMesAnoAusentes(quantidades, mesesExibicao);
        ordenarQuantidadesPorAnoMes(quantidades);

        return quantidades;
    }

    private void adicionarDataLabelToChart(ChartValues chartValues, List<QuantidadesPorAnoMes> quantidades) {
        for (QuantidadesPorAnoMes quantidade : quantidades) {
            chartValues.addData(quantidade.getQuantidade().intValue());
            chartValues.addLabel(DateTimeUtils.getShortName(Month.of(quantidade.getMes())));
        }
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

    private List<CardDto> criarCardsResumoDistribuicaoCesta() {
        final int periodoExibicaoEmMeses = obterConfiguracaoNoCache().getPeriodoExibicaoEmMeses();
        final LocalDateTime dataFim = DateTimeUtils.endOfMonth();
        final LocalDateTime dataInicio = DateTimeUtils.beginOfMonth().minusMonths(periodoExibicaoEmMeses);
        final ResumoDistribuicaoCestaDto resumo = service.obterResumoDeDistribuicaoCestas(dataInicio, dataFim);
        List<CardDto> cardsDto = new ArrayList<>();
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


    private void complementarQuantidadesComMesAnoAusentes(List<QuantidadesPorAnoMes> quantidades, List<YearMonth> mesesAnosDoPeriodo){
        mesesAnosDoPeriodo.stream().filter(mesAno -> nenhumaQuantidadeComMesmoMesAno(quantidades, mesAno))
            .map(mesAno -> new QuantidadesPorAnoMes(mesAno.getYear(), mesAno.getMonthValue(), 0L))
            .forEach(quantidades::add);
    }

    private void ordenarQuantidadesPorAnoMes(List<QuantidadesPorAnoMes> quantidades){
        quantidades.sort(Comparator.comparing(QuantidadesPorAnoMes::getAno).thenComparing(QuantidadesPorAnoMes::getMes));
    }

    private boolean nenhumaQuantidadeComMesmoMesAno(List<QuantidadesPorAnoMes> quantidades, YearMonth yearMonth) {
        return quantidades.stream().noneMatch(quantidade -> mesmoMesAno(quantidade, yearMonth));
    }

    private boolean mesmoMesAno(QuantidadesPorAnoMes quantidade, YearMonth yearMonth) {
        return quantidade.getAno().equals(yearMonth.getYear()) && quantidade.getMes().equals(yearMonth.getMonthValue());
    }

    @GetMapping("/configuracao")
    ResponseEntity<ConfiguracaoDashboardDto> obterConfiguracaoDashboard(){
        return ResponseEntity.ok(obterConfiguracaoNoCache());
    }

    @PostMapping("/salvar-configuracao")
    ResponseEntity<AjaxResponse> salvarConfiguracaoDashboard(@RequestBody ConfiguracaoDashboardDto config){
        cache.addItem(getClass(), KEY_DASHBOARD_CONFIG, AuthUtils.currentUsernameRequired(), config);

        AjaxResponse response = new AjaxResponse();
        response.addMessage("Configuração do dashboard atualizada.");
        return ResponseEntity.ok(response);
    }

}
