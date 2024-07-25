package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.commons.utils.ExceptionUtils;
import com.jnunes.spgcore.model.BaseEntity;
import com.jnunes.spgcore.model.BaseEntityId;
import com.jnunes.spgcore.services.BaseService;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.jnunes.spgdatatable.DataTablePage;
import com.jnunes.spgdatatable.DataTableRequest;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.CestaService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.function.Consumer;

@CommonsLog
@Controller
@RequestMapping("/cesta")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_CESTA')")
public class CestaController extends CrudControllerSec<Cesta> {

    CestaService service;

    @Autowired
    public void setService(CestaService serviceIn) {
        this.service = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("cesta/list");
    }

    @GetMapping
    ModelAndView viewPage(){
        return getModelAndViewListPage();
    }

    @PostMapping
    @ResponseBody
    DataTablePage<Cesta> datatableList(@RequestBody DataTableRequest pagingRequest) {
        return super.viewList(pagingRequest, service);
    }

    @GetMapping("/create")
    String create(Model model, @ModelAttribute("entity") final Cesta entity) {
        model.addAttribute("entity", new Cesta());
        return "cesta/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        @ModelAttribute Cesta entity, BindingResult result, RedirectAttributes attributes, Model model) {
        return internalSaveAndNew(entity, saveAndNew, "cesta", service, result, attributes, model);
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Cesta entity = service.findById(id).orElse(new Cesta());
        modelMap.addAttribute("entity", entity);
        return "cesta/create";
    }

    @ModelAttribute("cestaList")
    List<Cesta> autocomplete() {
        return service.findAll();
    }


    @PostMapping({"/delete"})
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        return super.deleteAll(ids, service::deleteById);
    }

}
