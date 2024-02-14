package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.commons.datatable.DataTablePage;
import com.jnunes.spgcore.commons.datatable.DataTableRequest;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.VoluntarioService;
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

@Controller
@RequestMapping("/voluntario")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_VOLUNTARIO')")
public class VoluntarioController extends CrudControllerSec<Voluntario> {

    VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("voluntario/list");
    }

    @GetMapping
    ModelAndView entitiesList() {
        return getModelAndViewListPage();
    }

    @PostMapping
    @ResponseBody
    DataTablePage<Voluntario> datatableList(@RequestBody DataTableRequest pagingRequest) {
        return super.viewList(pagingRequest, service);
    }

    @GetMapping("/create")
    String create(Model model) {
        model.addAttribute("entity", new Voluntario());
        return "voluntario/create";
    }

    @PostMapping("/save")
    String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Voluntario entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "voluntario/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/voluntario/create") : redirect("/voluntario/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Voluntario entity = service.findById(id).orElse(new Voluntario());
        modelMap.addAttribute("entity", entity);
        return "voluntario/create";
    }

    @PostMapping({"/delete"})
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        return super.deleteAll(ids, service::deleteById);
    }
}
