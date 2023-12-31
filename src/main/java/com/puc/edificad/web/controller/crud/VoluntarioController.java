package com.puc.edificad.web.controller.crud;

import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.VoluntarioService;
import com.jnunes.spgcore.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController extends CrudController<Voluntario> {

    private VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("voluntario/list");
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


}
