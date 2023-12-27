package com.puc.edificad.services.evento;

import com.puc.edificad.model.evento.ExecucaoEvento;
import com.puc.edificad.services.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ExecucaoEventoServiceImpl extends BaseServiceImpl<ExecucaoEvento> implements ExecucaoEventoService {

    private ExecucaoEventoRepository repository;

}
