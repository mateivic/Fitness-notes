package com.example.FitnessNotes.repository;


import com.example.FitnessNotes.model.Misicnagrupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MisicnaGrupaRepository extends JpaRepository<Misicnagrupa, Integer> {
}
