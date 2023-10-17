package com.puc.edificad.object_mother;

import com.puc.edificad.builder.VoluntarioBuilder;
import com.puc.edificad.model.Voluntario;

import java.time.LocalDate;
import java.util.List;

public class ObjMotherVoluntario extends ObjMotherBase {

    public static Voluntario criar() {
        return criar(1);
    }

    public static List<Voluntario> criarLista() {
        return criarInstancias(ObjMotherVoluntario::criar);
    }

    private static Voluntario criar(Integer i) {
        return new VoluntarioBuilder()
                .comParticipacaoEmProjetos(3 * i)
                .comNome("VOLUNTARIO - " + i)
                .comCPF("3030450670" + i)
                .comEmail("voluntario" + i + "@gmail.com")
                .comDataNascimento(LocalDate.of(1996, 12, i + 1))
                .comEndereco(null)
                .build();
    }

}
