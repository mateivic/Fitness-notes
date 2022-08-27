package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Klijent;
import com.example.FitnessNotes.model.Trening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreningRepository extends JpaRepository<Trening, Integer> {
    List<Trening> findAllByIdklijenta(Klijent klijent);
}
