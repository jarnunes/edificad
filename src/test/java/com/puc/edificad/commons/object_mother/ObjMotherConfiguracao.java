package com.puc.edificad.commons.object_mother;

import com.puc.edificad.commons.builder.ConfiguracaoBuilder;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.Configuracao;

public class ObjMotherConfiguracao extends ObjMotherBase {

    public static Configuracao criar() {
        return new ConfiguracaoBuilder()
                .comToken("secret-key1654dketkdisies-23943929s939393kdkgjti6989493920")
                .comDuracaoValidadeToken(1000)
                .build();
    }

    public static String criarJson() {
        Configuracao configuracao = ObjMotherConfiguracao.criar();
        return JsonUtils.toJsonString(configuracao);
    }

}
