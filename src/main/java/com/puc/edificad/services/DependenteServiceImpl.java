package com.puc.edificad.services;

import com.puc.edificad.mapper.DependenteMapper;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.dto.DependenteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DependenteServiceImpl extends BaseServiceImpl<Dependente> implements DependenteService {

    private DependenteMapper dependenteMapper;

    @Autowired
    public void setDependenteMapper(DependenteMapper mapper) {
        this.dependenteMapper = mapper;
    }

    @Override
    public List<DependenteDto> findAllDto() {
        return super.findAll().stream().map(dependenteMapper::toDto).toList();
    }

    @Override
    public DependenteDto save(DependenteDto dto) {
        Dependente entity = dependenteMapper.toEntity(dto);
        super.save(entity);
        return dependenteMapper.toDto(entity);
    }

    @Override
    public DependenteDto update(DependenteDto dto) {
        Dependente entity = dependenteMapper.toEntity(dto);
        super.update(entity);
        return dependenteMapper.toDto(entity);
    }
}
