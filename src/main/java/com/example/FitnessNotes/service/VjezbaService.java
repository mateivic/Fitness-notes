package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.MisicnaGrupaIVjezbeDTO;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VjezbaService {
    private VjezbaRepository vjezbaRepository;
    private MisicnaGrupaRepository misicnaGrupaRepository;
    private KlijentRepository klijentRepository;
    private RekordRepository rekordRepository;
    private VjezbeUTreninguRepository vjezbeUTreninguRepository;
    private TreningRepository treningRepository;


    public Vjezba dohvatiVjezbuPrekoId(Integer id) {
        if (vjezbaRepository.existsById(id)) {
            Vjezba vjezba = vjezbaRepository.getById(id);
            return vjezba;
        } else {
            System.out.println("Vježba ne postoji");
            throw new IllegalArgumentException("Vježba ne postoji");
        }
    }

    public List<MisicnaGrupaIVjezbeDTO> prikaziVjezbePoMisicnojGrupi() {
        List<Misicnagrupa> misicneGrupe = misicnaGrupaRepository.findAll();
        List<MisicnaGrupaIVjezbeDTO> lista = new ArrayList<>();
        for (var misicnaGrupa: misicneGrupe) {
            List<Vjezba> vjezbe = vjezbaRepository.findAllByIdmisicnegrupe(misicnaGrupa);
            MisicnaGrupaIVjezbeDTO dto = new MisicnaGrupaIVjezbeDTO(misicnaGrupa,
                    vjezbe);
            lista.add(dto);
        }
        return lista;
    }

    public boolean provjeriJeLiRekord(String email, LocalDate datumTreninga,
                                      Integer idVjezbe, Integer ponavljanja,
                                      Integer opterecenje) {
        Optional<Klijent> klijent = klijentRepository.findByMailklijenta(email);
        if (klijent.isPresent()) {
            Integer idKlijenta = klijent.get().getId();
            Double noviMax = calculateOneRepMax(ponavljanja, opterecenje);
            Optional<Rekord> stariRekord;
            stariRekord = rekordRepository
                        .findByIdKlijentaAndIdVjezbe(idKlijenta, idVjezbe);
            if (stariRekord.isEmpty()) {
                RekordId rekordId = new RekordId(datumTreninga, idKlijenta, idVjezbe);
                Rekord noviRekord = new Rekord(rekordId, noviMax.intValue());
                rekordRepository.save(noviRekord);
                return true;
            }
            if(stariRekord.get().getOnerepmax() < noviMax) {
                RekordId rekordId = new RekordId(datumTreninga, idKlijenta, idVjezbe);
                Rekord noviRekord = new Rekord(rekordId, noviMax.intValue());
                rekordRepository.delete(stariRekord.get());
                rekordRepository.save(noviRekord);
                return true;
            }
        }
        return false;
    }

    private Double calculateOneRepMax(Integer ponavljanja, Integer opterecenje) {
        if (opterecenje == 0)
            return 0.0;
        return opterecenje * (1 + (0.0333 * ponavljanja));
    }

    public void obrisiVjezbuUTreningu(Integer idTreninga, Integer idVjezbe) {
        VjezbeutreninguId id = new VjezbeutreninguId();
        id.setIdtreninga(idTreninga);
        id.setIdvjezbe(idVjezbe);
        if (vjezbeUTreninguRepository.existsById(id)) {
            vjezbeUTreninguRepository.deleteById(id);
        } else {
            System.out.println("Greška pri brisanju.");
            throw new IllegalArgumentException("Greška pri brisanju.");
        }
    }

    public void dodajVjezbuUTrening(Vjezbeutreningu vjezbeutreningu, Integer idTreninga, Integer idVjezbe) {
        if (treningRepository.existsById(idTreninga) && vjezbaRepository.existsById(idVjezbe) && vjezbeutreningu != null) {
            VjezbeutreninguId id= new VjezbeutreninguId();
            id.setIdtreninga(idTreninga);
            id.setIdvjezbe(idVjezbe);
            vjezbeutreningu.setId(id);
            vjezbeUTreninguRepository.save(vjezbeutreningu);
        } else {
            System.out.println("Argumenti nisu validni.");
            throw new IllegalArgumentException("Argumenti nisu validni.");
        }
    }

    public void dodajVjezbu(Vjezba vjezba, Integer misicnaGrupaId) {
        Optional<Misicnagrupa> misicnagrupa = misicnaGrupaRepository.findById(misicnaGrupaId);
        if (misicnagrupa.isPresent() && vjezba != null) {
            vjezba.setIdmisicnegrupe(misicnagrupa.get());
            vjezbaRepository.save(vjezba);
            return;
        }
        System.out.println("Greška u dodavnju vježbe");
        throw new IllegalArgumentException("Greška u dodavnju vježbe");
    }

    public void obrisiVjezbu(Integer id) {
        Optional<Vjezba> vjezba = vjezbaRepository.findById(id);
        if (vjezba.isPresent()) {
            vjezbaRepository.deleteById(id);
            return;
        }
        System.out.println("Greška u brisanju vježbe");
        throw new IllegalArgumentException("Greška u brisanju vježbe");
    }
}
