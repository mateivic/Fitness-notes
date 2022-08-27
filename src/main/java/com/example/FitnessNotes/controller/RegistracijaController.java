package com.example.FitnessNotes.controller;

import com.example.FitnessNotes.DTO.RegistracijaDTO;
import com.example.FitnessNotes.repository.DrzavaRepository;
import com.example.FitnessNotes.service.RegistracijaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class RegistracijaController {
    private RegistracijaService registracijaService;
    private DrzavaRepository drzavaRepository;

    @GetMapping
    public ModelAndView prikazRecistracijskeForme(Authentication auth) {
        if (auth != null) {
            return new ModelAndView("redirect:/login/router");
        }
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("form", new RegistracijaDTO());
        return mav;
    }

    @PostMapping
    public String registracija(@ModelAttribute RegistracijaDTO form) {
        System.out.println(form);
        try {
            registracijaService.register(form);
        } catch (Exception ex) {
            return "redirect:/?error=true";
        }
        return "redirect:/login?success=true";
    }
}
