package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.MjeraDTO;
import com.example.FitnessNotes.DTO.StatistikaDTO;
import com.example.FitnessNotes.DTO.TreningIVjezbeDTO;
import com.example.FitnessNotes.model.Klijent;
import com.example.FitnessNotes.model.Mjere;
import com.example.FitnessNotes.model.MjereId;
import com.example.FitnessNotes.repository.KlijentRepository;
import com.example.FitnessNotes.repository.MjereRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
@AllArgsConstructor
public class MjereService {
    private final MjereRepository mjereRepository;
    private final KlijentRepository klijentRepository;

    public List<MjeraDTO> getMjereKlijenta(Klijent klijent) {
        if (klijentRepository.existsById(klijent.getId())) {
            List<Mjere> mjere = mjereRepository.findByIdKlijenta(klijent.getId());
            List<MjeraDTO> mjereDto = new ArrayList<>();
            int i = 1;
            for (var mjera : mjere) {
                mjereDto.add(new MjeraDTO(i, mjera.getId().getDatummjerenja(),
                        mjera.getVisina(), mjera.getTezina(),
                        mjera.getOpsegstruka(), mjera.getOpsegprsa(), mjera.getOpsegruke()));
                i++;
            }
            Collections.sort(mjereDto, new Comparator<MjeraDTO>() {
                @Override
                public int compare(MjeraDTO o1, MjeraDTO o2) {
                    return o2.getDatum().compareTo(o1.getDatum());
                }
            });
            return mjereDto;
        } else {
            System.out.println("Klijent ne postoji.");
            throw new IllegalArgumentException("Klijent ne postoji.");
        }


    }

    public void dodajMjeru(Klijent klijent, MjeraDTO mjeraDto) {
        if (klijentRepository.existsById(klijent.getId()) && mjeraDto != null) {
            MjereId id = new MjereId(mjeraDto.getDatum(), klijent.getId());
            Mjere mjera = new Mjere(id, mjeraDto.getVisina(),
                    mjeraDto.getTezina(), mjeraDto.getOpsegStruka(),
                    mjeraDto.getOpsegPrsa(), mjeraDto.getOpsegRuke());
            mjereRepository.save(mjera);
            return;
        } else {
            System.out.println("Greška u dodavanju mjere.");
            throw new IllegalArgumentException("Greška u dodavanju mjere.");
        }
    }


    public void obrisiMjeru(MjereId id) {
        if (mjereRepository.existsById(id)) {
            mjereRepository.deleteById(id);
            return;
        } else {
            System.out.println("Mjera ne postoji");
            throw new IllegalArgumentException("Mjera ne postoji.");
        }
    }

    public MjeraDTO getMjera(Klijent klijent, LocalDate datum) {
        if (klijentRepository.existsById(klijent.getId()) && datum != null) {
            MjereId id = new MjereId(datum, klijent.getId());
            Mjere mjera = mjereRepository.getById(id);
            if (mjera != null) {
                MjeraDTO mjeraDTO = new MjeraDTO(null, datum, mjera.getVisina(),
                        mjera.getTezina(), mjera.getOpsegstruka(), mjera.getOpsegprsa(),
                        mjera.getOpsegruke());
                return mjeraDTO;
            }
            System.out.println("Mjera ne postoji");
            throw new IllegalArgumentException("Mjera ne postoji.");
        } else {
            System.out.println("Greška u dohvaćanju mjere.");
            throw new IllegalArgumentException("Greška u dohvaćanju mjere.");
        }
    }

    public void urediMjeru(Klijent klijent, LocalDate datum, MjeraDTO mjeraDto) {
        MjereId id = new MjereId(datum, klijent.getId());
        Optional<Mjere> staraMjera = mjereRepository.findById(id);
        if (staraMjera.isPresent()) {
            MjereId noviId = new MjereId(mjeraDto.getDatum(), klijent.getId());
            Mjere novaMjera = new Mjere(noviId, mjeraDto.getVisina(), mjeraDto.getTezina(),
                    mjeraDto.getOpsegStruka(), mjeraDto.getOpsegPrsa(), mjeraDto.getOpsegRuke());
            mjereRepository.delete(staraMjera.get());
            mjereRepository.save(novaMjera);
            return;
        }
        System.out.println("Greška u uređivanju mjere.");
        throw new IllegalArgumentException("Greška u uređivanju mjere.");
    }

    public List<StatistikaDTO> getStatistika(Integer id) {
        if (klijentRepository.existsById(id)) {
            List<StatistikaDTO> statistika = mjereRepository.findStatisticByIdKlijenta(id);
            return statistika;
        } else {
            System.out.println("Greška u pronalasku statistike");
            throw new IllegalArgumentException("Greška u pronalasku statistike");
        }
    }
}
