package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.commons.exceptions.ValidationException;
import com.jnunes.spgcore.web.CrudControllerSec;
import com.jnunes.spgcore.web.support.AjaxResponse;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.DependenteService;
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

@Controller
@RequestMapping("/dependente")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_DEPENDENTE')")
public class DependenteController extends CrudControllerSec<Dependente> {

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
    public String save(Dependente entity, BindingResult result, RedirectAttributes attributes, Model model) {
        return internalSaveAndNew(entity, false, "/dependente", service, result, attributes, model);
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
