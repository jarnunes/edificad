package com.puc.edificad.web.controller.distribuicao_cesta;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.VoluntarioService;
import com.puc.edificad.web.controller.CrudController;
import com.puc.edificad.web.support.AjaxResponse;
import com.puc.edificad.web.support.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("entity", new DistribuicaoCesta());
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


    @PostMapping("/delete")
    ResponseEntity<AjaxResponse> deleteAll(@RequestBody List<Long> ids) {
        int removedIds = 0;

        AjaxResponse response = new AjaxResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        for (Long id : ids) {
            try {
                service.deleteById(id);
                removedIds++;
            } catch (Exception e) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(getInternalError(e.getMessage()));
                return ResponseEntity.ok(response);
            }
        }
        response.addMessage(getSuccessDeleteMessage(removedIds));
        return ResponseEntity.ok(response);
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
