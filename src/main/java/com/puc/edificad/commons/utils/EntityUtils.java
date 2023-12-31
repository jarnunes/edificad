package com.puc.edificad.commons.utils;

import com.jnunes.spgcore.model.BaseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;

public class EntityUtils {

    private EntityUtils(){}

    public static <T extends BaseEntity> void setEntityId(LongConsumer entitySetter, Supplier<T> entityGetter){
        entitySetter.accept(getIdOrNull(entityGetter.get()));
    }

    public static<T extends BaseEntity> Long getIdOrNull(T entity){
        return Optional.ofNullable(entity).map(BaseEntity::getId).orElse(null);
    }

    public static <T extends BaseEntity> void setEntityLoad(Consumer<T> entitySetter, LongSupplier entityGetter, Function<Long, Optional<T>> loadService){
        entitySetter.accept(loadEntity(entityGetter.getAsLong(), loadService));
    }

    public static <T extends BaseEntity> void setEntitiesLoad(Consumer<Set<T>> entitySetter, Supplier<Set<Long>> entityGetter,
        Function<Long, Optional<T>> loadService){
        Set<T> entities = new HashSet<>();
        for(Long entityId : entityGetter.get()){
            entities.add(loadEntity(entityId, loadService));
        }
        entitySetter.accept(entities);
    }

    public static <T> T loadEntity(Long id, Function<Long, Optional<T>> loadService) {
        return Optional.ofNullable(id).map(loadService).flatMap(value -> value).orElse(null);
    }
}
