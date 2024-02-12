package com.puc.edificad.services;

import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.mapper.DistribuicaoCestaMapper;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
@Transactional
public class DistribuicaoCestaServiceImpl extends BaseServiceImpl<DistribuicaoCesta> implements DistribuicaoCestaService {

    private DistribuicaoCestaRepository repository;
    private DistribuicaoCestaMapper distribuicaoCestaMapper;

    private BeneficiarioService beneficiarioService;
    private CestaService cestaService;
    private VoluntarioService voluntarioService;

    @Autowired
    public void setDistribuicaoCestaMapper(DistribuicaoCestaMapper mapper) {
        this.distribuicaoCestaMapper = mapper;
    }


    @Autowired
    public void setRepository(DistribuicaoCestaRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn){
        this.beneficiarioService = serviceIn;
    }

    @Autowired
    public void setCestaService(CestaService serviceIn){
        this.cestaService = serviceIn;
    }

    @Autowired
    public void setVoluntarioService(VoluntarioService serviceIn){
        this.voluntarioService = serviceIn;
    }
    @Override
    public DistribuicaoCestaDto save(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        this.save(entity);
        return distribuicaoCestaMapper.toDto(entity);
    }

    @Override
    public DistribuicaoCesta save(DistribuicaoCesta entity) {
        cestaService.darBaixaDistribuicaoCesta(entity.getCesta().getId(), 1);
        ValidationUtils.validateDateTimeAfterNow(entity.getDataHora());

        return super.save(entity);
    }

    @Override
    public void update(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        ValidationUtils.validateDateTimeAfterNow(entity.getDataHora());
        super.update(entity);
    }

    @Override
    public List<DistribuicaoCestaDto> findAllDto() {
        return distribuicaoCestaMapper.toDtoList(super.findAll());
    }

    @Override
    public List<DistribuicaoCestaDto> findBy(String cesta, String cpfBeneficiario, String cpfVoluntario, LocalDate data) {
        List<DistribuicaoCesta> entities = repository.findByCestaBeneficiarioVoluntarioData(cesta, cpfBeneficiario,
                cpfVoluntario, data);
        return distribuicaoCestaMapper.toDtoList(entities);
    }

    @Override
    public ResumoDistribuicaoCestaDto obterResumoDeDistribuicaoCestas() {
        ResumoDistribuicaoCestaDto resumo = new ResumoDistribuicaoCestaDto();
        resumo.setCestasDistribuidas(repository.count());
        resumo.setBeneficiariosAssistidos(beneficiarioService.count());
        resumo.setQuantidadeEstoque(cestaService.count());
        return resumo;
    }

    @Override
    public List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidosPorMesAno(Year anoReferencia,
        Month mesReferencia, Long qtdMesesAnteriores){
        return processarObterQuantidadesPorMesAno(anoReferencia, mesReferencia, qtdMesesAnteriores,
            repository::obterQuantidadesBeneficiariosAssistidosEmUmPeriodo);
    }

    @Override
    public List<QuantidadesPorAnoMes> obterQuantidadeCestasDistribuidasPorMesAno(Year anoReferencia,
        Month mesReferencia, Long qtdMesesAnteriores){
        return processarObterQuantidadesPorMesAno(anoReferencia, mesReferencia, qtdMesesAnteriores,
            repository::obterQuantidadesCestasDistribuidasEmUmPeriodo);
    }

    private List<QuantidadesPorAnoMes> processarObterQuantidadesPorMesAno(Year anoReferencia,
        Month mesReferencia, Long qtdMesesAnteriores,
        BiFunction<LocalDateTime, LocalDateTime, List<QuantidadesPorAnoMes>> functionObterResultadoQuery){
        final LocalDateTime fimReferencia = DateTimeUtils.endOfLastDay(anoReferencia, mesReferencia);
        final LocalDateTime inicioReferencia = fimReferencia.minusMonths(qtdMesesAnteriores);

        List<QuantidadesPorAnoMes> resultado = functionObterResultadoQuery.apply(inicioReferencia, fimReferencia);

        resultado.sort(Comparator.comparing(QuantidadesPorAnoMes::getAno).thenComparing(QuantidadesPorAnoMes::getMes));
        return resultado;
    }

    @Override
    public Optional<DistribuicaoCesta> getEntityWithSearchAttrs(String searchValue) {
        DistribuicaoCesta entitySearch = new DistribuicaoCesta();
        beneficiarioService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setBeneficiario);
        cestaService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setCesta);
//        voluntarioService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setVoluntario);

        return Optional.of(entitySearch);
    }

    @Override
    public List<DistribuicaoCestaPorPeriodo> obterDistribuicaoPorPeriodo(LocalDate inicio, LocalDate fim, Cesta cesta,
        Beneficiario beneficiario, Voluntario voluntario) {
        return repository.obterDistribuicaoPorPeriodo(DateTimeUtils.toStartOfDay(inicio),
                DateTimeUtils.toEndOfDay(fim), cesta, beneficiario, voluntario);
    }
}
