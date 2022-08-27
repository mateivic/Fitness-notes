package com.example.FitnessNotes.controller;

import com.example.FitnessNotes.DTO.MisicnaGrupaIVjezbeDTO;
import com.example.FitnessNotes.DTO.TeretanaDTO;
import com.example.FitnessNotes.DTO.TrenerDTO;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.GradRepository;
import com.example.FitnessNotes.repository.KlijentRepository;
import com.example.FitnessNotes.repository.TeretanaRepository;
import com.example.FitnessNotes.repository.TrenerRepository;
import com.example.FitnessNotes.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    TeretanaRepository teretanaRepository;
    TrenerRepository trenerRepository;
    KlijentRepository klijentRepository;
    KlijentService klijentService;
    GradRepository gradRepository;
    TeretanaService teretanaService;
    TrenerService trenerService;
    VjezbaService vjezbaService;

    @GetMapping
    public String prikazAdminStranice(Authentication auth) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        return "admin";
    }

    @GetMapping("/teretane")
    public ModelAndView prikazTeretana(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Teretana> teretane = teretanaRepository.findAll();
        System.out.println(teretane);
        teretane = teretane.stream().sorted((a, b) -> a.getImeteretane().compareTo(b.getImeteretane()))
                .collect(Collectors.toList());
        System.out.println(teretane);
        ModelAndView mav = new ModelAndView("teretane");
        mav.addObject("admin", true);
        mav.addObject("teretane", teretane);
        return mav;
    }

    @GetMapping("/dodajTeretanu")
    public ModelAndView formaDodajTeretanu(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Grad> gradovi = gradRepository.findAll().stream()
                .sorted((a, b) -> a.getImegrada().compareTo(b.getImegrada()))
                .collect(Collectors.toList());
        List<Trener> treneri = trenerRepository.findAll().stream()
                .sorted((a, b) -> a.getPrezimetrenera().compareTo(b.getPrezimetrenera()))
                .collect(Collectors.toList());
        TeretanaDTO teretana = new TeretanaDTO();
        ModelAndView mav = new ModelAndView("dodaj_teretanu");
        mav.addObject("teretana", teretana);
        mav.addObject("gradovi", gradovi);
        mav.addObject("treneri", treneri);
        return mav;
    }

    @PostMapping("/dodajTeretanu")
    public String dodajTeretanu(Authentication auth,
                                @ModelAttribute TeretanaDTO teretana) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            teretanaService.dodajTeretanu(teretana);
            return "redirect:/admin/teretane/?addingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/dodajTeretanu/?error=true";
        }
    }

    @GetMapping("/obrisiTeretanu/{id}")
    public String obrisiTeretanu(Authentication auth,
                                 @PathVariable Integer id) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            teretanaService.obrisiTeretanu(id);
            return "redirect:/admin/teretane/?deletingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/teretane/?deletingError=true";
        }
    }

    @GetMapping("/urediTeretanu/{idTeretane}")
    public ModelAndView formaUrediTeretanu(Authentication auth,
                                           @PathVariable Integer idTeretane) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Grad> gradovi = gradRepository.findAll().stream()
                .sorted((a, b) -> a.getImegrada().compareTo(b.getImegrada()))
                .collect(Collectors.toList());
        List<Trener> treneri = trenerRepository.findAll().stream()
                .sorted((a, b) -> a.getPrezimetrenera().compareTo(b.getPrezimetrenera()))
                .collect(Collectors.toList());
        TeretanaDTO teretana = teretanaService.getTeretana(idTeretane);
        List<Integer> ideviTrenera = new ArrayList<>();
        for (int i = 0; i < teretana.getIdeviTrenera().length; i++) {
            ideviTrenera.add(teretana.getIdeviTrenera()[i]);
        }
        ModelAndView mav = new ModelAndView("uredi_teretanu");
        mav.addObject("teretana", teretana);
        mav.addObject("gradovi", gradovi);
        mav.addObject("treneri", treneri);
        mav.addObject("ideviTrenera", ideviTrenera);
        return mav;
    }

    @PostMapping("/urediTeretanu/{idTeretane}")
    public String urediTeretanu(Authentication auth,
                                @PathVariable Integer idTeretane,
                                @ModelAttribute TeretanaDTO teretanaDTO) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            teretanaService.urediTeretanu(idTeretane, teretanaDTO);
            return "redirect:/admin/teretane/?editingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/urediTeretanu/" + idTeretane + "/?error=true";
        }
    }


    @GetMapping("/treneri")
    public ModelAndView prikazTrenera(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Trener> treneri = trenerRepository.findAll().stream()
                .sorted((a, b) -> a.getPrezimetrenera().compareTo(b.getPrezimetrenera()))
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("treneri");
        mav.addObject("admin", true);
        mav.addObject("treneri", treneri);
        return mav;
    }

    @GetMapping("treneri/{idTeretane}")
    public ModelAndView prikazTreneraOdTeretane(Authentication auth,
                                                @PathVariable Integer idTeretane) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Trener> treneri = teretanaService.getSveTrenereTeretane(idTeretane);
        ModelAndView mav = new ModelAndView("treneri");
        mav.addObject("treneri", treneri);
        mav.addObject("admin", true);
        return mav;
    }

    @GetMapping("/dodajTrenera")
    public ModelAndView formaDodajTrenera(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        TrenerDTO trenerDTO = new TrenerDTO();
        List<Teretana> teretane = teretanaRepository.findAll().stream()
                .sorted((a, b) -> a.getImeteretane().compareTo(b.getImeteretane()))
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("dodaj_trenera");
        mav.addObject("trener", trenerDTO);
        mav.addObject("teretane", teretane);
        return mav;
    }

    @PostMapping("/dodajTrenera")
    public String dodajTrenera(Authentication auth,
                               @ModelAttribute TrenerDTO trenerDTO) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            String slikaString = (trenerService.ucitajSliku(trenerDTO.getSlika()));
            trenerDTO.setSlikaString(slikaString);
            trenerService.dodajTrenera(trenerDTO);
            return "redirect:/admin/treneri/?addingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/dodajTrenera/?error=true";
        }
    }

    @GetMapping("/urediTrenera/{id}")
    public ModelAndView formaUrediTrenera(Authentication auth,
                                          @PathVariable Integer id) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        TrenerDTO trener = trenerService.getTrenera(id);
        List<Teretana> teretane = teretanaRepository.findAll().stream()
                .sorted((a, b) -> a.getImeteretane().compareTo(b.getImeteretane()))
                .collect(Collectors.toList());
        List<Integer> ideviTeretana = new ArrayList<>();
        for (int i = 0; i < trener.getIdviTeretana().length; i++) {
            ideviTeretana.add(trener.getIdviTeretana()[i]);
        }
        ModelAndView mav = new ModelAndView("uredi_trenera");
        mav.addObject("trener", trener);
        mav.addObject("teretane", teretane);
        mav.addObject("ideviTeretana", ideviTeretana);
        return mav;
    }

    @PostMapping("/urediTrenera/{id}")
    public String urediTrenera(Authentication auth,
                               @PathVariable Integer id,
                               @ModelAttribute TrenerDTO trenerDTO) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            trenerService.urediTrenera(trenerDTO, id);
            return "redirect:/admin/treneri/?editingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/urediTrenera/" + id + "/?error=true";
        }
    }

    @GetMapping("/obrisiTrenera/{id}")
    public String obrisiTrenera(Authentication auth,
                                @PathVariable Integer id) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            trenerService.obrisiTrenera(id);
            return "redirect:/admin/treneri/?deletingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/treneri/?deletingError=true";
        }
    }

    @GetMapping("/klijenti")
    public ModelAndView prikaziKlijente(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Klijent> klijenti = klijentRepository.findAll().stream()
                .filter((a)-> a.getUloga().equals(UserRole.USER))
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("klijenti");
        mav.addObject("klijenti", klijenti);
        return mav;
    }

    @GetMapping("/obrisiKlijenta/{id}")
    public String obrisiKlijenta(Authentication auth,
                                 @PathVariable Integer id) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            klijentService.obrisiKlijenta(id);
            return "redirect:/admin/klijenti/?deletingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/klijenti/?deletingError=true";
        }
    }

    @GetMapping("/vjezbe")
    public ModelAndView prikaziVjezbe(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<MisicnaGrupaIVjezbeDTO> lista = vjezbaService.prikaziVjezbePoMisicnojGrupi();
        ModelAndView mav = new ModelAndView("vjezbe");
        mav.addObject("lista", lista);
        return mav;
    }

    @GetMapping("/dodajVjezbu/{misicnaGrupaId}")
    public ModelAndView formaDodajVjezbu(Authentication auth, @PathVariable Integer misicnaGrupaId) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Vjezba vjezba = new Vjezba();
        ModelAndView mav = new ModelAndView("dodaj_vjezbu");
        mav.addObject("misicnaGrupaId", misicnaGrupaId);
        mav.addObject("vjezba", vjezba);
        return mav;
    }

    @PostMapping("/dodajVjezbu/{misicnaGrupaId}")
    public String dodajVjezbu(Authentication auth,
                              @PathVariable Integer misicnaGrupaId,
                              @ModelAttribute Vjezba vjezba) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            vjezbaService.dodajVjezbu(vjezba, misicnaGrupaId);
            return "redirect:/admin/vjezbe/?addingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/dodajVjezbu/" + misicnaGrupaId + "/?error=true";
        }
    }

    @GetMapping("/obrisiVjezbu/{id}")
    public String obrisiVjezbu(Authentication auth,
                               @PathVariable Integer id) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            vjezbaService.obrisiVjezbu(id);
            return "redirect:/admin/vjezbe/?deletingSuccess=true";
        } catch (Exception e) {
            return "redirect:/admin/vjezbe/?deletingError=true";
        }
    }
}
