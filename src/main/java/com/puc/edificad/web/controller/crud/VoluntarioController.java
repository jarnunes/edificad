package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.service.dto.AutocompleteDto;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.PesquisaService;
import com.puc.edificad.services.VoluntarioService;
import com.jnunes.spgcore.web.CrudController;
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
@RequestMapping("/voluntario")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_VOLUNTARIO')")
public class VoluntarioController extends CrudControllerSec<Voluntario> {

    private VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("voluntario/list");
    }

    @GetMapping
    ModelAndView entitiesList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
        @RequestParam(value = "search",required = false) Optional<String> search,
        @RequestParam(value = "nav",defaultValue = "false",required = false) boolean nav) {
        return super.getEntitiesList(page, size, search, nav, service::findAll, service::findAll);
    }
    @GetMapping("/create")
    String create(Model model) {
        model.addAttribute("entity", new Voluntario());
        return "voluntario/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Voluntario entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "voluntario/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/voluntario/create") : redirect("/voluntario/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Voluntario entity = service.findById(id).orElse(new Voluntario());
        modelMap.addAttribute("entity", entity);
        return "voluntario/create";
    }

    @PostMapping({"/delete"})
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        return super.deleteAll(ids, service::deleteById);
    }
}
