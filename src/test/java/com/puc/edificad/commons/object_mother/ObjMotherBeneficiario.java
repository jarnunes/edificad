package com.puc.edificad.commons.object_mother;

import com.puc.edificad.commons.builder.BeneficiarioBuilder;
import com.puc.edificad.model.Beneficiario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ObjMotherBeneficiario extends ObjMotherBase {

    public static Beneficiario criar() {
        return criarBeneficiarioComIndice(1);
    }

    public static List<Beneficiario> criarLista5Entidades() {
        return criarInstancias(ObjMotherBeneficiario::criarBeneficiarioComIndice);
    }

    public static List<Beneficiario> criarLista5EntidadesUniqueKeyDuplicada() {
        List<Beneficiario> lista= new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> {
            lista.add(criar());
        });

        return lista;
    }

    public static Beneficiario criarBeneficiarioComIndice(Integer i) {
        return new BeneficiarioBuilder()
                .comNome("BENEFICIARIO - " + i)
                .comCPF("2030450670" + i)
                .comEmail("beneficiario_test" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, 1))
                .comEndereco(ObjMotherEndereco.criar())
                .build();
    }

}
