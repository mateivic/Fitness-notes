package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.MisicnaGrupaIVjezbeDTO;
import com.example.FitnessNotes.DTO.TreningDTO;
import com.example.FitnessNotes.DTO.TreningIVjezbeDTO;
import com.example.FitnessNotes.DTO.VjezbaUTreninguDTO;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Service
@AllArgsConstructor
public class TreningService {
    TreningRepository treningRepository;
    MisicnaGrupaRepository misicnaGrupaRepository;
    VjezbaRepository vjezbaRepository;
    VjezbeUTreninguRepository vjezbeUTreninguRepository;
    KlijentRepository klijentRepository;

    public Trening dohvatiTreningPrekoId(Integer id) {
        if (treningRepository.existsById(id)) {
            Trening trening = treningRepository.getById(id);
            return trening;
        } else {
            System.out.println("Trening ne postoji");
            throw new IllegalArgumentException("Trening ne postoji");
        }
    }


    public List<VjezbaUTreninguDTO> dohvatiAktivnostUTreningu(Integer id) {
        if (treningRepository.existsById(id)) {
            List<VjezbaUTreninguDTO> listaDto = new ArrayList<>();
            List<Vjezbeutreningu> lista = vjezbeUTreninguRepository.findByIdTreninga(id);
            for (var i: lista) {
                Optional<Vjezba> vjezba = vjezbaRepository.findById(i.getId().getIdvjezbe());
                if (vjezba.isPresent()) {
                    VjezbaUTreninguDTO vjezbaUTreningu = new VjezbaUTreninguDTO(i.getId().getIdvjezbe(), vjezba.get().getImevjezbe(),
                                                                                i.getBrojsetova(), i.getPonavljanja(), i.getOpterecenje(),
                                                                                i.getRpe());
                    listaDto.add(vjezbaUTreningu);
                }
            }
            return listaDto;
        } else {
            System.out.println("Trening ne postoji.");
            throw new IllegalArgumentException("Trening ne postoji.");
        }
    }


    public List<TreningIVjezbeDTO> getSviTreninziKlijenta(Klijent klijent) {
        try {
            if (klijentRepository.existsById(klijent.getId())) {
                List<Trening> treninzi = treningRepository.findAllByIdklijenta(klijent);
                List<TreningIVjezbeDTO> returnList = new ArrayList<>();
                for (var trening : treninzi) {
                    TreningIVjezbeDTO treningIVjezbe = new TreningIVjezbeDTO();

                    treningIVjezbe.setTrening(trening);
                    List<VjezbaUTreninguDTO> lista = new ArrayList<>();

                    List<Vjezbeutreningu> vjezbeUTreningu = vjezbeUTreninguRepository.findByIdTreninga(trening.getId());
                    for (var vjezbaUTreningu : vjezbeUTreningu) {
                        Optional<Vjezba> vjezba = vjezbaRepository.findById(vjezbaUTreningu.getId().getIdvjezbe());

                        VjezbaUTreninguDTO dto = new VjezbaUTreninguDTO();

                        dto.setBrojPonavljanja(vjezbaUTreningu.getPonavljanja());
                        dto.setBrojSetova(vjezbaUTreningu.getBrojsetova());
                        dto.setOpterecenje(vjezbaUTreningu.getOpterecenje());
                        dto.setRpe(vjezbaUTreningu.getRpe());
                        if (vjezba.isPresent()) {
                            dto.setIdVjezbe(vjezba.get().getId());
                            dto.setImeVjezbe(vjezba.get().getImevjezbe());
                        } else {
                            dto.setIdVjezbe(null);
                            dto.setImeVjezbe("Nepoznata vjezba");
                        }
                        lista.add(dto);
                    }
                    treningIVjezbe.setVjezbeUTreningu(lista);
                    returnList.add(treningIVjezbe);
                }
//SORTIRANJE PO DATUMU
                Collections.sort(returnList, new Comparator<TreningIVjezbeDTO>() {
                    @Override
                    public int compare(TreningIVjezbeDTO o1, TreningIVjezbeDTO o2) {
                        return o2.getTrening().getDatumtreninga().compareTo(o1.getTrening().getDatumtreninga());
                    }
                });
                return returnList;

            } else {
                System.out.println("Klijent ne postoji.");
                throw new IllegalArgumentException("Klijent ne postoji.");
            }
        } catch (Exception e) {
            System.out.println("Došlo je do pogreške.");
            throw new IllegalArgumentException("Došlo je do pogreške.");
        }

    }

    public void obrisiTrening(Integer id) {
        if (treningRepository.existsById(id)) {
            treningRepository.deleteById(id);
            return;
        } else {
            System.out.println("Trening ne postoji.");
            throw new IllegalArgumentException("Trening ne postoji.");
        }
    }

    public Trening dodajTrening(TreningDTO treningDto, Klijent klijent) {
        if(treningDto != null && klijent != null) {
            Trening trening = new Trening(treningDto.getDatum(),
                    treningDto.getTrajanje(),
                    klijent);

            treningRepository.save(trening);
            return trening;
        } else {
            System.out.println("Trening ili klijent ne postoji.");
            throw new IllegalArgumentException("Trening ili klijent ne postoji.");
        }
    }
}
