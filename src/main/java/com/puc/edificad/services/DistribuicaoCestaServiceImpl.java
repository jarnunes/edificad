package com.puc.edificad.services;

import com.puc.edificad.mapper.DistribuicaoCestaMapper;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DistribuicaoCestaServiceImpl extends BaseServiceImpl<DistribuicaoCesta> implements DistribuicaoCestaService {

    private DistribuicaoCestaMapper distribuicaoCestaMapper;

    @Autowired
    public void setDistribuicaoCestaMapper(DistribuicaoCestaMapper mapper) {
        this.distribuicaoCestaMapper = mapper;
    }


    @Override
    public DistribuicaoCestaDto save(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        super.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public void update(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = distribuicaoCestaMapper.toEntity(dto);
        super.update(entity);
    }

    @Override
    public List<DistribuicaoCestaDto> findAllDto() {
        return distribuicaoCestaMapper.doDtoList(super.findAll());
    }
}
