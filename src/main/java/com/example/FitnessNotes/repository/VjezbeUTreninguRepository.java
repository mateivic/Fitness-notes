package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.model.Vjezbeutreningu;
import com.example.FitnessNotes.model.VjezbeutreninguId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VjezbeUTreninguRepository extends JpaRepository<Vjezbeutreningu, VjezbeutreninguId> {
    @Query(value = "SELECT * FROM vjezbeutreningu WHERE idtreninga = ?1",
            nativeQuery = true)
    List<Vjezbeutreningu> findByIdTreninga(Integer id);
}
