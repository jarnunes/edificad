package com.puc.edificad.services;

import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.jnunes.spgcore.service.BaseServiceImpl;
import com.puc.edificad.mapper.BeneficiarioMapper;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BeneficiarioServiceImpl extends BaseServiceImpl<Beneficiario> implements BeneficiarioService {

    private BeneficiarioRepository repository;

    @Autowired
    public void setRepository(BeneficiarioRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    private BeneficiarioMapper beneficiarioMapper;

    @Autowired
    public void setBeneficiarioMapper(BeneficiarioMapper mapperIn){
        this.beneficiarioMapper = mapperIn;
    }
    @Override
    public List<Beneficiario> findByNomeCpf(String nome, String cpf) {
        return repository.findByNomeCpf(nome, cpf);
    }

    @Override
    public Beneficiario save(Beneficiario entity) {
        ValidationUtils.validateDateTimeAfterNow(entity.getDataNascimento());
        return super.save(entity);
    }

    @Override
    public Beneficiario update(BeneficiarioDto dto) {
        Beneficiario entity = beneficiarioMapper.toEntity(dto);
        ValidationUtils.validateDateTimeAfterNow(entity.getDataNascimento());
        return super.update(entity);
    }

    @Override
    public Optional<Beneficiario> getEntityWithSearchAttrs(String searchValue) {
        Beneficiario entitySearchExample = new Beneficiario();
        entitySearchExample.setNome(searchValue);
        entitySearchExample.setCpf(searchValue);
        return Optional.of(entitySearchExample);
    }
}
