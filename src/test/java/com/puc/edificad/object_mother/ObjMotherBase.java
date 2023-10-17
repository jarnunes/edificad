package com.puc.edificad.object_mother;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class ObjMotherBase {

    protected static <T> List<T> criarInstancias(Function<Integer, T> criarEntidade) {
        List<T> entities = new ArrayList<>();
        IntStream.range(0, 5).mapToObj(criarEntidade::apply).forEach(entities::add);
        return entities;
    }

}
