package com.puc.edificad.web.controller.crud;

import com.jnunes.spgcore.web.CrudController;
import com.puc.edificad.model.Birthplace;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/state")
public class StateController {

    private VoluntarioService service;

    @Autowired
    public void setService(VoluntarioService serviceIn) {
        this.service = serviceIn;
    }


    @GetMapping
    public String create(Model model) {
        model.addAttribute("birthplace", new Birthplace());
        return "state/create";
    }

    @PostMapping("/save")
    public String save(Birthplace entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "state/create";

        return "redirect:/voluntario/create";
    }


}
