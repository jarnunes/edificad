package com.puc.edificad.object_mother;

import com.puc.edificad.builder.DependenteBuilder;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;

import java.time.LocalDate;
import java.util.List;

public class ObjMotherDependente extends ObjMotherBase {

    public static Dependente criar() {
        return criarDependenteDeUmIndice(1);
    }

    public static List<Dependente> criarLista() {
        return criarInstancias(ObjMotherDependente::criarDependenteDeUmIndice);
    }

    public static DependenteDto criarDto() {
        DependenteDto dto = new DependenteDto();
        dto.copy(criar());
        return dto;
    }

    public static String criarDtoJson(){
        return  JsonUtils.toJsonString(criarDto());
    }

    private static Dependente criarDependenteDeUmIndice(Integer i) {
        return new DependenteBuilder()
                .comResponsavel(ObjMotherBeneficiario.criarBeneficiarioComIndice(i + 100))
                .comNome("DEPENDENTE - " + i)
                .comCPF("3030450670" + i)
                .comEmail("dependente" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, i + 1))
                .comEndereco(null)
                .build();
    }
}
