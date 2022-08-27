package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.RegistracijaDTO;
import com.example.FitnessNotes.model.Klijent;
import com.example.FitnessNotes.model.UserRole;
import com.example.FitnessNotes.repository.KlijentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistracijaService {
    KlijentRepository klijentRepository;
    KlijentService klijentService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(RegistracijaDTO registracijaDTO) {
        if(registracijaDTO == null) {
            System.out.println("Registriram nepostojućeg korisnika.");
            throw new IllegalArgumentException("Registriram nepostojućeg korisnika.");
        }
        if (!ispravnostPonovljeneLozinke(registracijaDTO.getLozinka(),registracijaDTO.getPonovljenaLozinka())) {
            System.out.println("Korisničko ime ili lozinka nisu ispravni.");
            throw new IllegalStateException("Korisničko ime ili lozinka nisu ispravni.");
        }
        if (klijentService.vecPostojiEmail(registracijaDTO.getEmail())) {
            System.out.println("Korisnik već postoji.");
            throw new IllegalArgumentException("Korisnik već postoji.");
        }
        String sifriranaLozika = bCryptPasswordEncoder.encode(registracijaDTO.getLozinka());

        Klijent klijent = new Klijent(registracijaDTO.getIme(),
                registracijaDTO.getPrezime(),
                registracijaDTO.getEmail(),
                sifriranaLozika,
                UserRole.USER);

        klijentRepository.save(klijent);
    }

    public boolean ispravnostPonovljeneLozinke(String lozinka1, String lozinka2) {
        if (lozinka1.equals(lozinka2))
            return true;
        else
            return false;
    }


}
