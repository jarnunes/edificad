package com.puc.edificad.web.controller;


import com.puc.edificad.commons.config.Message;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.web.config.Properties;
import com.puc.edificad.web.support.AjaxResponse;
import com.puc.edificad.web.support.MessagesAlert;
import com.puc.edificad.web.support.StatusCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
public abstract class AbstractController<T extends BaseEntity> {

    protected static final String ENTITY = "entity";

    @Autowired
    @Lazy
    protected Message message;

    @Autowired
    protected Properties properties;

    @Getter
    @Setter
    protected Map<String, String> params;
    protected MessagesAlert alert = new MessagesAlert();

    @SuppressWarnings("unchecked")
    protected final Class<T> entityClass =
            (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        alert = new MessagesAlert();
    }

    protected String getSuccessSaveMessage() {
        return message.get("success.save.entity", entityClass.getSimpleName());
    }

    protected String getSuccessUpdateMessage() {
        return message.get("success.update.entity", entityClass.getSimpleName());
    }

    protected String getSuccessDeleteMessage() {
        return message.get("success.delete.entity", entityClass.getSimpleName());
    }

    protected String getUpdateOrCreateSuccessMessage(Long entityId) {
        return nonNull(entityId) ? getSuccessUpdateMessage() : getSuccessSaveMessage();
    }

    protected void addSuccess(AjaxResponse ajaxResponse, Long entityId) {
        ajaxResponse.addMessage(getUpdateOrCreateSuccessMessage(entityId));
    }

    protected void addSuccess(ModelMap model, final String message) {
        alert.addSuccess(message);
        addMessageAlert(model);
    }

    protected void addSuccess(RedirectAttributes model, final String message) {
        alert.addSuccess(message);
        addMessageAlert(model);
    }

    protected void addSuccessKey(RedirectAttributes model, final String msgKey, Object... args) {
        alert.addSuccess(message.get(msgKey, args));
        addMessageAlert(model);
    }

    protected void addSuccess(RedirectAttributes attributes, Long entityId) {
        addSuccess(attributes, getUpdateOrCreateSuccessMessage(entityId));
    }

    protected void addErrorMessage(AjaxResponse ajaxResponse, Exception e) {
        ajaxResponse.setStatusCode(StatusCode.ERROR);
        ajaxResponse.addMessage(e.getMessage());
    }

    protected void addSuccessDeleteMessage(AjaxResponse ajaxResponse) {
        ajaxResponse.setStatusCode(StatusCode.SUCCESS);
        ajaxResponse.addMessage(getSuccessDeleteMessage());
    }

    protected String getSuccessDeleteMessage(int recordsNumber) {
        return message.get("success.delete.entity.list", entityClass.getSimpleName(), recordsNumber);
    }

    protected String getInternalError(Object ... errorDetails) {
        return message.get("err.delete.entity", errorDetails);
    }

    protected String getInternalError(Throwable throwable) {
        return message.get("err.delete.entity", throwable.getMessage());
    }

    protected void addError(ModelMap model, final String message) {
        alert.addError(message);
        addMessageAlert(model);
    }

    protected void addError(RedirectAttributes model, final String message) {
        alert.addError(message);
        addMessageAlert(model);
    }

    protected void addErrorKey(RedirectAttributes model, final String msgKey, Object... args) {
        alert.addError(message.get(msgKey, args));
        addMessageAlert(model);
    }

    protected void addWarning(ModelMap model, final String message) {
        alert.addWarning(message);
        addMessageAlert(model);
    }

    protected void addWarning(RedirectAttributes model, final String message) {
        alert.addWarning(message);
        addMessageAlert(model);
    }

    private void addMessageAlert(ModelMap model) {
        model.addAttribute("messages", alert);
    }

    private void addMessageAlert(RedirectAttributes model) {
        model.addFlashAttribute("messages", alert);
    }

    protected String redirect(String path) {
        return "redirect:" + path;
    }

    protected String redirect(String path, Object parameter) {
        return "redirect:" + path + "/" + parameter;
    }

    protected void addEntityToModelMap(ModelMap modelMap, T entity) {
        modelMap.addAttribute(ENTITY, entity);
    }

}
