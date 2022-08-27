package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Klijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface KlijentRepository extends JpaRepository<Klijent, Integer> {

    Optional<Klijent> findByMailklijenta(String mailKlijenta);

    Boolean existsByMailklijenta(String mailKlijenta);
}
