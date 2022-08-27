package com.example.FitnessNotes.controller;

import com.example.FitnessNotes.DTO.*;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.RekordRepository;
import com.example.FitnessNotes.repository.TeretanaRepository;
import com.example.FitnessNotes.service.*;
import lombok.AllArgsConstructor;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/profil")
public class KlijentController {
    KlijentService klijentService;
    TreningService treningService;
    VjezbaService vjezbaService;
    TeretanaRepository teretanaRepository;
    RekordService rekordService;
    MjereService mjereService;
    TeretanaService teretanaService;

    @GetMapping
    public ModelAndView prikazKlijentStranice(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        System.out.println(auth.getAuthorities() + " " + auth.getCredentials());
        ModelAndView mav = new ModelAndView("profil_klijent");
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        mav.addObject("klijent", klijent);
        return mav;
    }

    @GetMapping("/dodajTrening")
    public ModelAndView formaZaTrening(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<MisicnaGrupaIVjezbeDTO> lista = vjezbaService.prikaziVjezbePoMisicnojGrupi();
        System.out.println(lista);
        ModelAndView mav = new ModelAndView("dodaj_trening");
        mav.addObject("trening", new TreningDTO());
        return mav;
    }

    @PostMapping("/dodajTrening")
    public String dodajTrening(Authentication auth,
                               @ModelAttribute TreningDTO trening) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            Trening noviTrening = treningService.dodajTrening(trening, klijent);

            int id = noviTrening.getId();
            return "redirect:/profil/dodajVjezbe/" + id;
        } catch (Exception e) {
            return "redirect:/profil/dodajTrening/?error=true";
        }
    }

    @GetMapping("/dodajVjezbe/{id}")
    public ModelAndView misicneGrupe(Authentication auth,
                                     @PathVariable(value = "id") Integer id) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<VjezbaUTreninguDTO> vjezbe = treningService.dohvatiAktivnostUTreningu(id);
        Trening trening = treningService.dohvatiTreningPrekoId(id);
        List<MisicnaGrupaIVjezbeDTO> lista = vjezbaService.prikaziVjezbePoMisicnojGrupi();
        ModelAndView mav = new ModelAndView("dodaj_vjezbe_u_trening");
        mav.addObject("vjezbe", vjezbe);
        mav.addObject("trening", trening);
        mav.addObject("lista", lista);
        return mav;
    }

    @GetMapping("/dodajVjezbe/{idTreninga}/{idVjezbe}")
    public ModelAndView formaZaVjezbu(Authentication auth,
                                      @PathVariable(value = "idTreninga") Integer idTreninga,
                                      @PathVariable(value = "idVjezbe") Integer idVjezbe) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Trening trening = treningService.dohvatiTreningPrekoId(idTreninga);
        Vjezba vjezba = vjezbaService.dohvatiVjezbuPrekoId(idVjezbe);
        ModelAndView mav = new ModelAndView("dodaj_vjezbu_u_trening");
        mav.addObject("vjezbeUTreningu", new Vjezbeutreningu());
        mav.addObject("trening", trening);
        mav.addObject("vjezba", vjezba);
        return mav;
    }

    @PostMapping("/dodajVjezbe/{idTreninga}/{idVjezbe}")
    public String dodajVjezbuUTrening(Authentication auth,
                                      @PathVariable(value = "idTreninga") Integer idTreninga,
                                      @PathVariable(value = "idVjezbe") Integer idVjezbe,
                                      @ModelAttribute Vjezbeutreningu vjezbeutreningu) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        System.out.println(vjezbeutreningu);
        try {
            vjezbaService.dodajVjezbuUTrening(vjezbeutreningu, idTreninga, idVjezbe);
            Trening trening = treningService.dohvatiTreningPrekoId(idTreninga);
            boolean rekord = vjezbaService.provjeriJeLiRekord(auth.getName(),
                    trening.getDatumtreninga(), idVjezbe,
                    vjezbeutreningu.getPonavljanja(),
                    vjezbeutreningu.getOpterecenje());
            if (rekord) {
                return "redirect:/profil/dodajVjezbe/" + idTreninga + "/?rekord=true";
            } else {
                return "redirect:/profil/dodajVjezbe/" + idTreninga;
            }
        } catch (Exception e) {
            return "redirect:/profil/dodajVjezbe/" + idTreninga + "/" + idVjezbe + "/?error=true";
        }
    }

    @GetMapping("/obrisiVjezbuUTreningu/{idTreninga}/{idVjezbe}")
    public String obrisiVjezbuUTreningu(Authentication auth,
                                        @PathVariable(value = "idTreninga") Integer idTreninga,
                                        @PathVariable(value = "idVjezbe") Integer idVjezbe) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            vjezbaService.obrisiVjezbuUTreningu(idTreninga, idVjezbe);
            return "redirect:/profil/dodajVjezbe/" + idTreninga;
        } catch (Exception e) {
            return "redirect:/profil/dodajVjezbe/" + idTreninga + "/?error=true";
        }
    }

    @GetMapping("/osobniPodaci")
    public ModelAndView prikazOsobnihPodataka(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        List<Teretana> teretane = teretanaRepository.findAll();
        OsobniPodaciDTO podaci;
        if (klijent.getIdteretane() == null) {
            podaci = new OsobniPodaciDTO(klijent.getImeklijenta(), klijent.getPrezimeklijenta(),
                    klijent.getDatumrodklijenta(), 0, "Trenutno nemate svoju teretanu.");
        } else {
            podaci = new OsobniPodaciDTO(klijent.getImeklijenta(), klijent.getPrezimeklijenta(),
                    klijent.getDatumrodklijenta(), klijent.getIdteretane().getId(), klijent.getIdteretane().getImeteretane());
        }
        ModelAndView mav = new ModelAndView("prikaz_osobnih_podataka");
        mav.addObject("teretane", teretane);
        mav.addObject("klijent", podaci);
        return mav;
    }

    @PostMapping("/osobniPodaci")
    public String azurirajOsobnePodatke(Authentication auth,
                                        @ModelAttribute OsobniPodaciDTO osobniPodaci) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            klijentService.azurirajKlijenta(klijent, osobniPodaci);
            return "redirect:/profil/osobniPodaci/?success=true";
        } catch (Exception e) {
            return "redirect:/profil/osobniPodaci/?error=true";
        }
    }

    @GetMapping("/treninzi")
    public ModelAndView prikaziTreningeKlijenta(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        List<TreningIVjezbeDTO> treninziIVjezbe = treningService.getSviTreninziKlijenta(klijent);
        ModelAndView mav = new ModelAndView("treninzi_klijenta");
        mav.addObject("treninziIVjezbe", treninziIVjezbe);
        return mav;
    }

    @GetMapping("/obrisiTrening/{id}")
    public String obrisiTrening(Authentication auth, @PathVariable(value = "id") Integer id) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            treningService.obrisiTrening(id);
            return "redirect:/profil/treninzi/?success=true";
        } catch (Exception ex) {
            return "redirect:/profil/treninzi/?error=true";
        }
    }

    @GetMapping("/rekordi")
    public ModelAndView prikaziRekorde(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        List<RekordDTO> rekordi = klijentService.dohvatiRekordeKlijenta(klijent);
        ModelAndView mav = new ModelAndView("rekordi");
        mav.addObject("rekordi", rekordi);
        return mav;
    }

    @GetMapping("/obrisiRekord/{datumString}/{idVjezbe}")
    public String obrisiTrening(Authentication auth,
                                @PathVariable String datumString,
                                @PathVariable Integer idVjezbe) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate datum = LocalDate.parse(datumString, formatter);
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            RekordId id = new RekordId(datum, klijent.getId(), idVjezbe);
            rekordService.obrisiRekord(id);
            return "redirect:/profil/rekordi/?success=true";
        } catch (Exception e) {
            return "redirect:/profil/rekordi/?error=true";
        }

    }

    @GetMapping("/mjere")
    public ModelAndView prikaziMjereKlijenta(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        List<MjeraDTO> mjere = mjereService.getMjereKlijenta(klijent);
        ModelAndView mav = new ModelAndView("mjere");
        mav.addObject("mjere", mjere);
        return mav;
    }

    @GetMapping("/dodajMjeru")
    public ModelAndView formaDodajMjeru(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        MjeraDTO mjera = new MjeraDTO();
        ModelAndView mav = new ModelAndView("dodaj_mjeru");
        mav.addObject("mjera", mjera);
        return mav;
    }

    @PostMapping("/dodajMjeru")
    public String dodajMjeru(Authentication auth,
                             @ModelAttribute MjeraDTO mjera) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            mjereService.dodajMjeru(klijent, mjera);
            return "redirect:/profil/mjere/?addingSuccess=true";
        } catch (Exception e) {
            return "redirect:/profil/dodajMjeru/?error=true";
        }
    }

    @GetMapping("/obrisiMjeru/{datumString}")
    public String obrisiMjeru(Authentication auth,
                              @PathVariable String datumString) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate datum = LocalDate.parse(datumString, formatter);
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            mjereService.obrisiMjeru(new MjereId(datum, klijent.getId()));
            return "redirect:/profil/mjere/?deleteSuccess=true";
        } catch (Exception e) {
            return "redirect:/profil/mjere/?deleteError=true";
        }
    }

    @GetMapping("/urediMjeru/{datumString}")
    public ModelAndView formaUrediMjeru(Authentication auth,
                                        @PathVariable String datumString) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datum = LocalDate.parse(datumString, formatter);
        Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
        MjeraDTO mjera = mjereService.getMjera(klijent, datum);

        ModelAndView mav = new ModelAndView("uredi_mjeru");
        mav.addObject("mjera", mjera);
        return mav;
    }

    @PostMapping("/urediMjeru/{datumString}")
    public String urediMjeru(Authentication auth,
                             @PathVariable String datumString,
                             @ModelAttribute MjeraDTO mjera) {
        if (auth == null) {
            return "redirect:/login/router";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate datum = LocalDate.parse(datumString, formatter);
            Klijent klijent = klijentService.getKlijentFromAuth(auth.getName());
            mjereService.urediMjeru(klijent, datum, mjera);
            return "redirect:/profil/mjere/?editSuccess=true";
        } catch (Exception e) {
            return "redirect:/profil/urediMjeru/" + datumString + "/?error=true";
        }
    }

    @GetMapping("/teretane")
    public ModelAndView prikazTeretana(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Teretana> teretane = teretanaRepository.findAll().stream().
                sorted((a, b) -> a.getImeteretane().compareTo(b.getImeteretane()))
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("teretane");
        mav.addObject("admin", false);
        mav.addObject("teretane", teretane);
        return mav;
    }

    @GetMapping("/treneri/{idTeretane}")
    public ModelAndView prikaziTrenereTeretane(Authentication auth,
                                               @PathVariable Integer idTeretane) {
        if (auth == null) {
            return new ModelAndView("redirect:/login/router");
        }
        List<Trener> treneri = teretanaService.getSveTrenereTeretane(idTeretane);
        ModelAndView mav = new ModelAndView("treneri");
        mav.addObject("treneri", treneri);
        mav.addObject("admin", false);
        return mav;
    }

}
