package com.puc.edificad.services;

import com.puc.edificad.model.Dependente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DependenteServiceImpl extends BaseServiceImpl<Dependente> implements DependenteService {
}
