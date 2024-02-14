package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.mapper.BeneficiarioMapper;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.services.validation.BeneficiarioValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BeneficiarioServiceImpl extends PersonServiceImpl<Beneficiario> implements BeneficiarioService {

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
    public Beneficiario update(BeneficiarioDto dto) {
        return super.update(beneficiarioMapper.toEntity(dto));
    }



    @Override
    public Optional<Beneficiario> getEntityWithSearchAttrs(String searchValue) {
        Beneficiario entitySearchExample = new Beneficiario();
        entitySearchExample.setNome(searchValue);
        entitySearchExample.setCpf(searchValue);
        return Optional.of(entitySearchExample);
    }
}
