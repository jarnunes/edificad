package com.puc.edificad.object_mother;

import com.puc.edificad.builder.BeneficiarioBuilder;
import com.puc.edificad.model.Beneficiario;

import java.time.LocalDate;
import java.util.List;

public class ObjMotherBeneficiario extends ObjMotherBase {

    public static Beneficiario criar() {
        return criarBeneficiarioComIndice(1);
    }

    public static List<Beneficiario> criarLista() {
        return criarInstancias(ObjMotherBeneficiario::criarBeneficiarioComIndice);
    }

    public static Beneficiario criarBeneficiarioComIndice(Integer i) {
        return new BeneficiarioBuilder()
                .comNome("BENEFICIARIO - " + i)
                .comCPF("3030450670" + i)
                .comEmail("beneficiario" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, 1))
                .comEndereco(null)
                .build();
    }

}
