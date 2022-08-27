package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Teretana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeretanaRepository extends JpaRepository<Teretana, Integer> {
    Optional<Teretana> findByImeteretane(String imeTeretane);
}
