package com.puc.edificad.web.response;

import com.puc.edificad.commons.utils.MessageUtils;
import com.puc.edificad.model.BaseEntity;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class BaseResponse<T extends BaseEntity> extends ResponseEntity<T> {

    public BaseResponse(HttpStatusCode status) {
        super(status);
    }

    public static <T> ResponseEntity<List<T>> of(List<T> body) {
        return CollectionUtils.isEmpty(body) ? notFound().build() : ResponseEntity.ok().body(body);
    }

    public static ResponseEntity<String> of(String msgKey, Object...args){
        return ResponseEntity.ok(MessageUtils.get(msgKey, args));
    }
}
