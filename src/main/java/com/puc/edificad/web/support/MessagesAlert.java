package com.puc.edificad.web.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MessagesAlert {

    private final List<String> success = new ArrayList<>();
    private final List<String> error = new ArrayList<>();
    private final List<String> warning = new ArrayList<>();

    public MessagesAlert(Exception e) {
        addError(e.getMessage());
    }

    public boolean withSuccess() {
        return CollectionUtils.isNotEmpty(success);
    }

    public boolean withError() {
        return CollectionUtils.isNotEmpty(error);
    }

    public boolean withWarning() {
        return CollectionUtils.isNotEmpty(warning);
    }

    public void addSuccess(String message) {
        success.add(message);
    }

    public void addError(String errorMessage) {
        error.add(errorMessage);
    }

    public void addWarning(String warningMessage) {
        warning.add(warningMessage);
    }

}
