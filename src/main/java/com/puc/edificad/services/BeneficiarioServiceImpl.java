package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BeneficiarioServiceImpl extends BaseServiceImpl<Beneficiario> implements BeneficiarioService {
}
