package com.puc.edificad.services;

import com.puc.edificad.model.Voluntario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VoluntarioServiceImpl extends BaseServiceImpl<Voluntario> implements VoluntarioService {
}
