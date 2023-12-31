package com.puc.edificad.web.controller.crud;

import com.jnunes.core.commons.exceptions.ValidationException;
import com.jnunes.core.commons.utils.ValidationUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.DependenteService;
import com.puc.edificad.web.controller.CrudController;
import com.puc.edificad.web.support.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dependente")
public class DependenteController extends CrudController<Dependente> {

    private DependenteService service;

    private BeneficiarioService beneficiarioService;

    @Autowired
    public void setService(DependenteService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("dependente/list");
    }

    @GetMapping("/create")
    String create(@RequestParam("beneficiario") Long beneficiario, Model model) {
        Dependente dependente = new Dependente();
        dependente.setResponsavel(obterResponsavel(beneficiario));
        model.addAttribute("entity", dependente);
        return "dependente/create";
    }

    private Beneficiario obterResponsavel(Long beneficiario){
        return beneficiarioService.findById(beneficiario).orElseThrow(() ->
                new ValidationException("dependente.responsavel.nao.encontrado", beneficiario));
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        Dependente entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "dependente/create";
        final Long entityId = entity.getId();

        service.save(entity);
        addSuccess(attributes, entityId);

        return saveAndNew ? redirect("/dependente/create") : redirect("/dependente/update", entity.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        Dependente entity = service.findById(id).orElse(new Dependente());
        modelMap.addAttribute("entity", entity);
        return "dependente/create";
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<AjaxResponse> delete(@PathVariable Long id) {
        int removedIds = 0;

        AjaxResponse response = new AjaxResponse();
            try {
                service.deleteById(id);
                removedIds++;
            } catch (Exception e) {
                response.addMessage(getInternalError(e.getMessage()));
                return ResponseEntity.internalServerError().body(response);
            }
        response.addMessage(getSuccessDeleteMessage(removedIds));
        return ResponseEntity.ok(response);
    }

}
