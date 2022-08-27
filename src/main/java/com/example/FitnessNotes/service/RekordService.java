package com.example.FitnessNotes.service;

import com.example.FitnessNotes.model.RekordId;
import com.example.FitnessNotes.repository.RekordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RekordService {
    private final RekordRepository rekordRepository;

    public void obrisiRekord(RekordId id) {
        if (rekordRepository.existsById(id)) {
            rekordRepository.deleteById(id);
            return;
        } else {
            System.out.println("Rekord ne postoji");
            throw new IllegalArgumentException("Rekord ne postoji.");
        }
    }
}
