package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.service.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.PesquisaService;
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
import java.util.Optional;

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
    ModelAndView entitiesList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
        @RequestParam(value = "search",required = false) Optional<String> search,
        @RequestParam(value = "nav",defaultValue = "false",required = false) boolean nav) {
        return super.getEntitiesList(page, size, search, nav, service::findAll, service::findAll);
    }

    @GetMapping("/create")
    String create(Model model) {
        model.addAttribute("entity", new Cesta());
        return "cesta/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Cesta entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "cesta/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/cesta/create") : redirect("/cesta/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Cesta entity = service.findById(id).orElse(new Cesta());
        modelMap.addAttribute("entity", entity);
        return "cesta/create";
    }

    @ModelAttribute("cestaList")
    List<Cesta> autocomplete(){
        return service.findAll();
    }


    @PostMapping({"/delete"})
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        return super.deleteAll(ids, service::deleteById);
    }

}
