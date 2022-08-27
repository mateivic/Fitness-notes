package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Teretanatreneri;
import com.example.FitnessNotes.model.TeretanatreneriId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeretanaTreneriRepository extends JpaRepository<Teretanatreneri, TeretanatreneriId> {

    @Query(value = "SELECT * FROM teretanatreneri WHERE idteretane = ?1",
    nativeQuery = true)
    List<Teretanatreneri> findAllByIdTeretane(Integer idTeretane);

    @Query(value = "SELECT * FROM teretanatreneri WHERE idtrenera = ?1",
    nativeQuery = true)
    List<Teretanatreneri> findAllByIdTrenera(Integer idTrenera);
}
