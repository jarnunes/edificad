package com.puc.edificad.web.controller.crud;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.web.controller.CrudController;
import com.puc.edificad.web.support.AjaxResponse;
import com.puc.edificad.web.support.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/beneficiario")
public class BeneficiarioController extends CrudController<Beneficiario> {

    private BeneficiarioService service;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.service = serviceIn;
    }

//    @GetMapping
//    String list(ModelMap modelMap){
//        modelMap.addAttribute("entities", service.findAll());
//        return "beneficiario/list";
//    }

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

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("beneficiario/list");
    }
}
