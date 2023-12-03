package com.puc.edificad.commons.object_mother;

import com.puc.edificad.commons.builder.EnderecoBuilder;
import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.Estado;

import java.util.List;

public class ObjMotherEndereco extends ObjMotherBase {

    public static Endereco criar() {
        return criar(1);
    }

    public static List<Endereco> criarLista() {
        return criarInstancias(ObjMotherEndereco::criar);
    }

    private static Endereco criar(Integer i) {
        return new EnderecoBuilder()
                .comLogradouro("Rua das ostras " + i)
                .comNumero("Nro " + i)
                .comCep("31.545-17" + i)
                .comBairro("Bairro das ostras_" + i)
                .comCidade("Cidade das Ostras_" + i)
                .comEstado(Estado.MG).build();
    }

}
