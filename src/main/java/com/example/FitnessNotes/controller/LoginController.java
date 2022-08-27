package com.example.FitnessNotes.controller;

import com.example.FitnessNotes.DTO.LoginDTO;
import com.example.FitnessNotes.model.Klijent;
import com.example.FitnessNotes.service.KlijentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private KlijentService klijentService;

    @GetMapping
    public ModelAndView prikazLoginFormu(Authentication auth) {
        if (auth != null) {
            return new ModelAndView("redirect:/login/router");
        }
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginDto", new LoginDTO());
        return mav;
    }

    @GetMapping("/router")
    public String redirect(HttpServletRequest req) {
        if(req.getUserPrincipal() == null) return "redirect:/login";
        if (req.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        } else {
            return "redirect:/profil";
        }
    }
}
