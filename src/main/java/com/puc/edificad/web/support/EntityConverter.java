package com.puc.edificad.web.support;

import com.jnunes.spgcore.model.BaseEntity;
import com.jnunes.spgcore.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class EntityConverter<T extends BaseEntity> implements Converter<String, T> {

    private BaseService<T> entityService;

    @Autowired
    public void setEntityService(BaseService<T> entityServiceIn) {
        this.entityService = entityServiceIn;
    }

    @Override
    public T convert(@NonNull String id) {
        return Optional.of(id).map(StringUtils::trimToNull).map(Long::parseLong)
                .flatMap(entityService::findById).orElse(null);
    }


}
