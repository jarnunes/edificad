package com.puc.edificad.web.controller.crud;

import com.puc.edificad.model.Birthplace;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/state")
public class StateController {

    @GetMapping
    public String create(Model model) {
        model.addAttribute("birthplace", new Birthplace());
        return "state/create";
    }

    @PostMapping("/save")
    public String save(Birthplace entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "create-bacakup";

        return "redirect:/state";
    }


}
