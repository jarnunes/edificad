package com.puc.edificad.services.evento;

import com.puc.edificad.model.evento.Evento;
import com.puc.edificad.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EventoServiceImpl extends BaseServiceImpl<Evento> implements EventoService {

    private final EventoRepository repository;
    private final ExecucaoEventoService execucaoEventoService;

    @Autowired
    public EventoServiceImpl(EventoRepository repositoryIn, ExecucaoEventoService execucaoEventoServiceIn) {
        this.repository = repositoryIn;
        this.execucaoEventoService = execucaoEventoServiceIn;
    }


}
