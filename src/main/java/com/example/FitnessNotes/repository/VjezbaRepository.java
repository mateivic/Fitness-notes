package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Misicnagrupa;
import com.example.FitnessNotes.model.Vjezba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VjezbaRepository extends JpaRepository<Vjezba, Integer> {
    List<Vjezba> findAllByIdmisicnegrupe(Misicnagrupa misicnagrupa);
}
