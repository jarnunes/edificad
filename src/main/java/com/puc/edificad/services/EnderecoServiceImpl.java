package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseServiceImpl;
import com.puc.edificad.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EnderecoServiceImpl extends BaseServiceImpl<Endereco> implements EnderecoService {

}
