package com.puc.edificad.services;

import com.puc.edificad.commons.utils.DateTimeUtils;
import com.puc.edificad.mapper.DistribuicaoCestaMapper;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
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
        return super.save(entity);
    }

    @Override
    public void update(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
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
        final Month primeiroMesReferencia = mesReferencia.minus(qtdMesesAnteriores);
        final LocalDateTime inicioReferencia = DateTimeUtils.beginOfFirstDay(anoReferencia, primeiroMesReferencia);
        final LocalDateTime fimReferencia = DateTimeUtils.endOfLastDay(anoReferencia, mesReferencia);

        List<QuantidadesPorAnoMes> resultado = functionObterResultadoQuery.apply(inicioReferencia, fimReferencia);

        resultado.sort(Comparator.comparing(QuantidadesPorAnoMes::getAno).thenComparing(QuantidadesPorAnoMes::getMes));
        return resultado;
    }

    @Override
    public Optional<DistribuicaoCesta> getEntityWithSearchAttrs(String searchValue) {
        DistribuicaoCesta entitySearch = new DistribuicaoCesta();
        beneficiarioService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setBeneficiario);
        cestaService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setCesta);
        voluntarioService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setVoluntario);

        return Optional.of(entitySearch);
    }
}
