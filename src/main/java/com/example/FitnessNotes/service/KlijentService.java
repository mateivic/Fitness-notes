package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.*;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@AllArgsConstructor
@Service
public class KlijentService {
    private final KlijentRepository klijentRepository;
    private final TeretanaRepository teretanaRepository;
    private final RekordRepository rekordRepository;
    private final VjezbaRepository vjezbaRepository;

//    public Klijent login(LoginDTO loginDTO) throws IllegalAccessException {
//        if(loginDTO == null) {
//            System.out.println("Logiram nepostojućeg korisnika.");
//            throw new IllegalArgumentException("Logiram nepostojućeg korisnika.");
//        }
//
//        Optional<Klijent> klijentOpt = klijentRepository.findByMailklijenta(loginDTO.getEmail());
//        if (klijentOpt.isPresent()) {
//            Klijent klijent = klijentOpt.get();
//            String lozinka = loginDTO.getLozinka();
//            if(!bCryptPasswordEncoder.matches(lozinka, klijent.getPassword())) {
//                System.out.println("Lozinka netocna.");
//                throw new IllegalAccessException("Lozinka netocna.");
//            }
//            return klijent;
//        }
//        System.out.println("Klijent ne postoji.");
//        throw new IllegalAccessException("Klijent ne postoji.");
//
//    }

    public boolean vecPostojiEmail(String email) {
        return klijentRepository.existsByMailklijenta(email);
    }

    public Klijent getKlijentFromAuth(String email) {
        Optional<Klijent> klijent = klijentRepository.findByMailklijenta(email);
        if (klijent.isPresent()) {
            return klijent.get();
        }
        System.out.println("Klijent ne postoji");
        throw new IllegalArgumentException("Klijent ne postoji");
    }

    public void azurirajKlijenta(Klijent stari, OsobniPodaciDTO osobniPodaci) {
        if (osobniPodaci != null && klijentRepository.existsById(stari.getId())) {
            Klijent azuriran = new Klijent();
            azuriran.setId(stari.getId());
            azuriran.setImeklijenta(osobniPodaci.getIme());
            azuriran.setPrezimeklijenta(osobniPodaci.getPrezime());
            azuriran.setDatumrodklijenta(osobniPodaci.getDatumRod());
            azuriran.setMailklijenta(stari.getMailklijenta());
            azuriran.setLozinkaklijenta(stari.getLozinkaklijenta());
            azuriran.setUloga(stari.getUloga());
            if (teretanaRepository.existsById(osobniPodaci.getTeretanaId())) {
                Teretana teretana = teretanaRepository.getById(osobniPodaci.getTeretanaId());
                azuriran.setIdteretane(teretana);
                klijentRepository.save(azuriran);
                return;
            } else {
                System.out.println("Teretana ne postoji.");
                throw new IllegalArgumentException("Teretana ne postoji.");
            }
        }
        System.out.println("Podaci za promjenu su neispravni ili korisnik ne postoji.");
        throw new IllegalArgumentException("Podaci za promjenu su neispravni ili korisnik ne postoji.");
    }

    public List<RekordDTO> dohvatiRekordeKlijenta(Klijent klijent) {
        if (klijentRepository.existsById(klijent.getId())) {
            List<Rekord> rekordi = rekordRepository.findAllByIdKlijenta(klijent.getId());
            List<RekordDTO> rekordiDto = new ArrayList<>();
            int i = 1;
            for (var rekord: rekordi) {
                Optional<Vjezba> vjezba = vjezbaRepository.findById(rekord.getId().getIdvjezbe());
                if(vjezba.isPresent()) {
                    rekordiDto.add(new RekordDTO(i, rekord, vjezba.get().getImevjezbe()));
                } else {
                    rekordiDto.add(new RekordDTO(i, rekord, "Nepoznata vježba"));
                }
                i++;
            }
            Collections.sort(rekordiDto, new Comparator<RekordDTO>() {
                @Override
                public int compare(RekordDTO o1, RekordDTO o2) {
                    return o2.getRekord().getId().getDatumrekorda().compareTo(o1.getRekord().getId().getDatumrekorda());
                }
            });
            return rekordiDto;
        } else {
            System.out.println("Klijent ne postoji");
            throw new IllegalArgumentException("Klijent ne postoji");
        }
    }

    public void obrisiKlijenta(Integer id) {
        if (klijentRepository.existsById(id)) {
            klijentRepository.deleteById(id);
            return;
        } else {
            System.out.println("Klijent ne postoji");
            throw new IllegalArgumentException("Klijent ne postoji");
        }
    }
}
