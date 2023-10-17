package com.puc.edificad.object_mother;

import com.puc.edificad.builder.CestaBuilder;
import com.puc.edificad.model.Cesta;

import java.util.List;

public class ObjMotherCesta extends ObjMotherBase {

    public static Cesta criar() {
        return criarCestaComIndice(1);
    }

    public static List<Cesta> criarLista() {
        return criarInstancias(ObjMotherCesta::criarCestaComIndice);
    }

    private static Cesta criarCestaComIndice(Integer i) {
        return new CestaBuilder()
                .comNome("CESTA - " + i)
                .comDescricao("CESTA - " + i + " para familias xxx")
                .comQuantidadeEstoque(i * 2)
                .build();
    }

}
