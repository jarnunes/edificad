package com.puc.edificad.commons.object_mother;

import com.puc.edificad.commons.builder.DistribuicaoCestaBuilder;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.CestaDto;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.model.dto.VoluntarioDto;

import java.time.LocalDateTime;
import java.util.List;

public class ObjMotherDistribuicaoCesta extends ObjMotherBase {

    public static DistribuicaoCestaDto criar() {
        return criarComIndice(1);
    }

    public static List<DistribuicaoCestaDto> criarLista() {
        return criarInstancias(ObjMotherDistribuicaoCesta::criarComIndice);
    }

    public static String criarJson() {
        DistribuicaoCestaDto cesta = ObjMotherDistribuicaoCesta.criar();
        return JsonUtils.toJsonString(cesta);
    }

    private static DistribuicaoCestaDto criarComIndice(Integer i) {
        return new DistribuicaoCestaBuilder()
                .comBeneficiario(new BeneficiarioDto())
                .comVoluntario(new VoluntarioDto())
                .comCesta(new CestaDto())
                .comDataHora(LocalDateTime.now())
                .build();
    }

}
