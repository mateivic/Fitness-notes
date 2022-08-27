package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Rekord;
import com.example.FitnessNotes.model.RekordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RekordRepository  extends JpaRepository<Rekord, RekordId> {
    @Query(value = "SELECT * FROM rekord WHERE idklijenta = ?1 and idvjezbe = ?2",
    nativeQuery = true)
    Optional<Rekord> findByIdKlijentaAndIdVjezbe(Integer idKlijenta, Integer idVjezbe);

    @Query(value = "SELECT * FROM rekord WHERE idklijenta = ?1",
    nativeQuery = true)
    List<Rekord> findAllByIdKlijenta(Integer idKlijenta);

}
