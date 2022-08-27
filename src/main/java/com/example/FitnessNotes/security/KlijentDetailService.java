package com.example.FitnessNotes.security;

import com.example.FitnessNotes.model.Klijent;
import com.example.FitnessNotes.repository.KlijentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class KlijentDetailService implements UserDetailsService {
    private final KlijentRepository klijentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Klijent> klijentOptional = klijentRepository.findByMailklijenta(email);
        if (!klijentOptional.isPresent()) {
            System.out.println("Klijent " + email + " nije pronaden!");
            throw new UsernameNotFoundException("Klijent " + email + " nije pronaden!");
        }

        Klijent klijent = klijentOptional.get();
        return klijent;
    }
}
