package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Trener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrenerRepository extends JpaRepository<Trener, Integer> {
}
