package com.puc.edificad.services;

import com.puc.edificad.model.Cesta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CestaServiceImpl extends BaseServiceImpl<Cesta> implements CestaService {
}
