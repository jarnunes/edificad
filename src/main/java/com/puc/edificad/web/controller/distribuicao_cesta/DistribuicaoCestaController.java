package com.puc.edificad.web.controller.distribuicao_cesta;

import com.jnunes.spgcore.web.CrudController;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/distribuicao-cesta")
public class DistribuicaoCestaController extends CrudController<DistribuicaoCesta> {

    private DistribuicaoCestaService service;

    private BeneficiarioService beneficiarioService;
    private VoluntarioService voluntarioService;
    private CestaService cestaService;


    @Autowired
    public void setService(DistribuicaoCestaService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    private void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    @Autowired
    private void setVoluntarioService(VoluntarioService serviceIn) {
        this.voluntarioService = serviceIn;
    }

    @Autowired
    private void setCestaService(CestaService serviceIn) {
        this.cestaService = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("distribuicao-cesta/list");
    }

    @GetMapping("/create")
    String create(Model model) {
        DistribuicaoCesta entity = new DistribuicaoCesta();
        entity.setDataHora(LocalDateTime.now());
        model.addAttribute("entity", entity);
        return "distribuicao-cesta/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        DistribuicaoCesta entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "distribuicao-cesta/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/distribuicao-cesta/create") : redirect("/distribuicao-cesta/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        DistribuicaoCesta entity = service.findById(id).orElse(new DistribuicaoCesta());
        modelMap.addAttribute("entity", entity);
        return "distribuicao-cesta/create";
    }

    @ModelAttribute("beneficiarioList")
    List<Beneficiario> beneficiarioList() {
        return beneficiarioService.findAll();
    }

    @ModelAttribute("voluntarioList")
    List<Voluntario> voluntarioList() {
        return voluntarioService.findAll();
    }

    @ModelAttribute("cestaList")
    List<Cesta> cestaList() {
        return cestaService.findAll();
    }
}
