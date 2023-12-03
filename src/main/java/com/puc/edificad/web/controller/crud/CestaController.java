package com.puc.edificad.web.controller.crud;

import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.CestaService;
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
@RequestMapping("/cesta")
public class CestaController extends CrudController<Cesta> {

    private CestaService service;

    @Autowired
    public void setService(CestaService serviceIn) {
        this.service = serviceIn;
    }

    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("cesta/list");
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

    @ModelAttribute("cestaList")
    List<Cesta> autocomplete(){
        return service.findAll();
    }


}
