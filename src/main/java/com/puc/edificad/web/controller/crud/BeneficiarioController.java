package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.web.CrudController;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/beneficiario")
public class BeneficiarioController extends CrudController<Beneficiario> {

    private BeneficiarioService service;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.service = serviceIn;
    }


    @GetMapping("/create")
    String create(Model model) {
        model.addAttribute("entity", new Beneficiario());
        return "beneficiario/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Beneficiario entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "beneficiario/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/beneficiario/create") : redirect("/beneficiario/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Beneficiario entity = service.findById(id).orElse(new Beneficiario());
        modelMap.addAttribute("entity", entity);
        return "beneficiario/create";
    }


    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("beneficiario/list");
    }
}
