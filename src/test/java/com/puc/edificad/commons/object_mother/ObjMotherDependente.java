package com.puc.edificad.commons.object_mother;

import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.puc.edificad.commons.builder.DependenteBuilder;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ObjMotherDependente extends ObjMotherBase {

    public static Dependente criar() {
        return criarDependenteDeUmIndice(1);
    }

    public static List<Dependente> criarLista5Entidades() {
        return criarInstancias(ObjMotherDependente::criarDependenteDeUmIndice);
    }

    public static DependenteDto criarDto() {
        DependenteDto dto = new DependenteDto();
        dto.copy(criar());
        return dto;
    }

    public static List<DependenteDto> criarLista5Dtos(){
        List<DependenteDto> dtos = new ArrayList<>();
        criarLista5Entidades().forEach(entity -> {
            DependenteDto dto = new DependenteDto();
            dto.copy(entity);
            dtos.add(dto);
        });

        return dtos;
    }

    public static List<DependenteDto> criarLista5DtosUkDuplicado(){
        List<DependenteDto> dtos = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> {
            dtos.add(criarDto());
        });
        return dtos;
    }

    public static String criarDtoJson(){
        return  JsonUtils.toJsonString(criarDto());
    }

    private static Dependente criarDependenteDeUmIndice(Integer i) {
        return new DependenteBuilder()
                .comResponsavel(ObjMotherBeneficiario.criarBeneficiarioComIndice(i + 100))
                .comNome("DEPENDENTE - " + i)
                .comCPF("070394567" + i)
                .comEmail("dependente" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, i + 1))
                .comEndereco(null)
                .build();
    }
}
