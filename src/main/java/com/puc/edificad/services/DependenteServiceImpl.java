package com.puc.edificad.services;

import com.puc.edificad.mapper.DependenteMapper;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;
import com.puc.edificad.services.config.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DependenteServiceImpl extends PersonServiceImpl<Dependente> implements DependenteService {

    private DependenteMapper dependenteMapper;
    private ParametroService parametroService;

    @Autowired
    public void setDependenteMapper(DependenteMapper mapper) {
        this.dependenteMapper = mapper;
    }

    @Autowired
    void setParametroService(ParametroService parametroService) {
        this.parametroService = parametroService;
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
        return dependenteMapper.toDto(super.update(entity));
    }
}
