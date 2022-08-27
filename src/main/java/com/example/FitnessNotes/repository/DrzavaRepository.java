package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Drzava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrzavaRepository extends JpaRepository<Drzava, Integer> {

}
