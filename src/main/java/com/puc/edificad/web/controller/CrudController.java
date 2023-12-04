package com.puc.edificad.web.controller;


import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ExceptionUtils;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.services.BaseService;
import com.puc.edificad.web.support.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public abstract class CrudController<T extends BaseEntity> extends AbstractController<T> {

    private BaseService<T> service;
    protected ResourceLoader resourceLoader;

    @Autowired
    public void setService(BaseService<T> service) {
        this.service = service;
    }

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    private void saveEntities(AtomicInteger entitiesSaved, List<T> entitiesList) {
        entitiesList.forEach(entity -> {
            service.save(entity);
            entitiesSaved.getAndIncrement();
        });
    }


    @GetMapping
    ModelAndView entitiesList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
        @RequestParam(value = "search", required = false) Optional<String> search,
        @RequestParam(value = "nav", defaultValue = "false", required = false) boolean nav) {

        AtomicReference<Page<T>> entitiesPage = new AtomicReference<>();
        search.ifPresentOrElse(searchV -> entitiesPage.set(getEntitiesPageSearch(nav, page, size, searchV)),
                () -> entitiesPage.set(getEntitiesPage(page, size)));

        ModelAndView modelAndView = getModelAndViewListPage();
        modelAndView.addObject("entities", entitiesPage.get().getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("size", size);
        modelAndView.addObject("totalPages", entitiesPage.get().getTotalPages());
        return modelAndView;
    }

    private Page<T> getEntitiesPage(final int page, final int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.findAll(pageable);
    }

    private Page<T> getEntitiesPageSearch(final boolean nav, final int page, final int size, final String searchValue){
        Pageable pageable = PageRequest.of(nav ? page : 0 , size);
        return service.findAll(searchValue, pageable);
    }

    protected abstract ModelAndView getModelAndViewListPage();


    @PostMapping("/delete")
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        int qtdeRegistrosRemovidos = 0;
        List<Long> idsRemovidos = new ArrayList<>();

        AjaxResponse response = new AjaxResponse();
        for (Long id : ids) {
            try {
                T entity = service.findById(id).orElseThrow(EntityNotFoundException::notFoundForId);
                try {
                    //service.deleteById(id);
                    qtdeRegistrosRemovidos++;
                    idsRemovidos.add(id);
                } catch (Exception e) {
                    response.setData(idsRemovidos);
                    response.addMessage(getInternalError(entity.getId(), ExceptionUtils.getRootCause(e)));
                    response.addMessage("Apenas " + qtdeRegistrosRemovidos + " registros foram removidos");
                    return ResponseEntity.internalServerError().body(response);
                }
            } catch (Exception e) {
                response.addMessage(e.getMessage());
                return ResponseEntity.internalServerError().body(response);
            }

        }
        response.addMessage(getSuccessDeleteMessage(qtdeRegistrosRemovidos));
        response.setData(idsRemovidos);
        return ResponseEntity.ok(response);
    }


}
