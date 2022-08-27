package com.example.FitnessNotes.repository;

import com.example.FitnessNotes.DTO.StatistikaDTO;
import com.example.FitnessNotes.model.Mjere;
import com.example.FitnessNotes.model.MjereId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MjereRepository extends JpaRepository<Mjere, MjereId> {
    @Query(value = "SELECT * FROM mjere WHERE idklijenta = ?1",
    nativeQuery = true)
    List<Mjere> findByIdKlijenta(Integer id);

    @Query(value = "SELECT extract(month from datummjerenja) as mjesecmjerenja, AVG(tezina) as tezina, AVG(opsegprsa) as opsegprsa, " +
            "AVG(opsegstruka) as opsegstruka, AVG(opsegruke) as opsegruke FROM mjere " +
            "WHERE idklijenta = ?1 and " +
            "EXTRACT(year from datummjerenja) = EXTRACT(year from CURRENT_DATE)" +
            " GROUP BY mjesecmjerenja " +
            "ORDER BY mjesecmjerenja",
            nativeQuery = true)
    List<StatistikaDTO> findStatisticByIdKlijenta(Integer id);
}
