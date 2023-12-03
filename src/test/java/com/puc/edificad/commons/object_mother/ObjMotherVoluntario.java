package com.puc.edificad.commons.object_mother;

import com.puc.edificad.commons.builder.VoluntarioBuilder;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.Voluntario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ObjMotherVoluntario extends ObjMotherBase {

    public static Voluntario criar() {
        return criar(1);
    }

    public static String criarJson(){
        return JsonUtils.toJsonString(criar());
    }

    public static List<Voluntario> criarLista5Entidades() {
        return criarInstancias(ObjMotherVoluntario::criar);
    }

    public static List<Voluntario> criarLista5EntidadesUniqueKeyDuplicada() {
        List<Voluntario> lista= new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> {
            lista.add(criar());
        });

        return lista;
    }

    private static Voluntario criar(Integer i) {
        return new VoluntarioBuilder()
                .comParticipacaoEmProjetos(3 * i)
                .comNome("VOLUNTARIO - " + i)
                .comCPF("3030450670" + i)
                .comEmail("voluntario" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, i + 1))
                .comTelefone("(13) 99999-999"+i)
                .comEndereco(ObjMotherEndereco.criar())
                .build();
    }

}
