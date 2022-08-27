package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Grad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradRepository extends JpaRepository<Grad, Integer> {
}
