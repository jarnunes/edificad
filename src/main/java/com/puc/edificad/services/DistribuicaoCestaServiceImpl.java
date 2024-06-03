package com.puc.edificad.services;

import com.jnunes.spgauth.commons.utils.AuthUtils;
import com.jnunes.spgcore.commons.utils.DateTimeUtils;
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
import com.puc.edificad.services.validation.DistribuicaoCestaValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DistribuicaoCestaServiceImpl extends BaseServiceImpl<DistribuicaoCesta> implements DistribuicaoCestaService {

    private DistribuicaoCestaRepository repository;
    private DistribuicaoCestaMapper distribuicaoCestaMapper;

    private BeneficiarioService beneficiarioService;
    private CestaService cestaService;

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

    @Override
    public DistribuicaoCestaDto save(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        this.save(entity);
        return distribuicaoCestaMapper.toDto(entity);
    }

    @Override
    public DistribuicaoCesta save(DistribuicaoCesta entity) {
        DistribuicaoCestaValidation validation = new DistribuicaoCestaValidation(entity);
        validation.validarQuantidadeEstoque();
        validation.validarSeDataHoraEntregaSuperiorDataHoraAtual();

        cestaService.darBaixaDistribuicaoCesta(entity.getCesta().getId(), 1);
        return super.save(entity);
    }

    @Override
    public void update(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        DistribuicaoCestaValidation validation = new DistribuicaoCestaValidation(entity);
        validation.validarQuantidadeEstoque();
        validation.validarSeDataHoraEntregaSuperiorDataHoraAtual();

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
    public ResumoDistribuicaoCestaDto obterResumoDeDistribuicaoCestas(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia) {
        ResumoDistribuicaoCestaDto resumo = repository.obterResumoDeDistribuicaoCestas(dataInicioReferencia, dataFimReferencia);
        resumo.setQuantidadeEstoque(cestaService.count());
        return resumo;
    }

    @Override
    public List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidosPorMesAno(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia){
        return repository.obterQuantidadesBeneficiariosAssistidosEmUmPeriodo(dataInicioReferencia, dataFimReferencia);
    }

    @Override
    public List<QuantidadesPorAnoMes> obterQuantidadeCestasDistribuidasPorPeriodo(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia){
        return repository.obterQuantidadesCestasDistribuidasEmUmPeriodo(dataInicioReferencia, dataFimReferencia);
    }

    @Override
    public Optional<DistribuicaoCesta> getEntityWithSearchAttrs(String searchValue) {
        DistribuicaoCesta entitySearch = new DistribuicaoCesta();
        beneficiarioService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setBeneficiario);
        cestaService.getEntityWithSearchAttrs(searchValue).ifPresent(entitySearch::setCesta);

        return Optional.of(entitySearch);
    }

    @Override
    public List<DistribuicaoCestaPorPeriodo> obterDistribuicaoPorPeriodo(LocalDate inicio, LocalDate fim, Cesta cesta,
        Beneficiario beneficiario, Voluntario voluntario) {
        return repository.obterDistribuicaoPorPeriodo(DateTimeUtils.toStartOfDay(inicio),
                DateTimeUtils.toEndOfDay(fim), cesta, beneficiario, voluntario);
    }

    @Override
    public void cancelarDistribuicaoCesta(Long idDistribuicaoCesta, String motivoCancelamento) {
        DistribuicaoCesta distribuicaoCesta = findById(idDistribuicaoCesta).orElseThrow();
        distribuicaoCesta.setCancelamento(LocalDateTime.now());
        distribuicaoCesta.setMotivoCancelamento(motivoCancelamento);
        distribuicaoCesta.setUsuarioCancelamento(AuthUtils.currentUsernameRequired());

        DistribuicaoCestaValidation validation = new DistribuicaoCestaValidation(distribuicaoCesta);
        validation.validarSePermiteCancelarDistribuicao();
        validation.validarSeExisteJustificativaCancelamento();
        update(distribuicaoCesta);

        cestaService.contabilizarCestaEmEstoqueNoCancelamentoDistribuicaoCesta(distribuicaoCesta.getCesta().getId());
    }

    @Override
    public Page<DistribuicaoCesta> obterDistribuicaoCestasNaoCanceladas(String searchValue, Pageable pageable) {
        return repository.obterDistribuicaoCestasNaoCanceladas(searchValue, pageable);
    }
}
