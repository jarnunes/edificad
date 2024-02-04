package com.puc.edificad.commons.object_mother;

import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.puc.edificad.commons.builder.CestaBuilder;

import com.puc.edificad.model.Cesta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ObjMotherCesta extends ObjMotherBase {

    public static Cesta criar() {
        return criarCestaComIndice(1);
    }

    public static List<Cesta> criarLista5Entidades() {
        return criarInstancias(ObjMotherCesta::criarCestaComIndice);
    }

    public static List<Cesta> criarLista5EntidadesUniqueKeyDuplicada() {
        List<Cesta> lista= new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> {
            lista.add(criar());
        });

        return lista;
    }

    public static String criarJson() {
        Cesta cesta = ObjMotherCesta.criar();
        return JsonUtils.toJsonString(cesta);
    }

    private static Cesta criarCestaComIndice(Integer i) {
        return new CestaBuilder()
                .comNome("CESTA - " + i)
                .comDescricao("CESTA - " + i + " para familias xxx")
                .comQuantidadeEstoque(i * 2)
                .build();
    }

}
