package com.puc.edificad.services;

import com.puc.edificad.mapper.DistribuicaoCestaMapper;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DistribuicaoCestaServiceImpl extends BaseServiceImpl<DistribuicaoCesta> implements DistribuicaoCestaService {

    private DistribuicaoCestaRepository repository;
    private DistribuicaoCestaMapper distribuicaoCestaMapper;

    @Autowired
    public void setDistribuicaoCestaMapper(DistribuicaoCestaMapper mapper) {
        this.distribuicaoCestaMapper = mapper;
    }


    @Autowired
    public void setRepository(DistribuicaoCestaRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public DistribuicaoCestaDto save(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        super.save(entity);
        return distribuicaoCestaMapper.toDto(entity);
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
}
