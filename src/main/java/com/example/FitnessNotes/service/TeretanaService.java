package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.TeretanaDTO;
import com.example.FitnessNotes.model.*;
import com.example.FitnessNotes.repository.GradRepository;
import com.example.FitnessNotes.repository.TeretanaRepository;
import com.example.FitnessNotes.repository.TeretanaTreneriRepository;
import com.example.FitnessNotes.repository.TrenerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeretanaService {
    private final TeretanaRepository teretanaRepository;
    private final TeretanaTreneriRepository teretanaTreneriRepository;
    private final TrenerRepository trenerRepository;
    private final GradRepository gradRepository;

    public List<Trener> getSveTrenereTeretane(Integer idTeretane) {
        if (teretanaRepository.existsById(idTeretane)) {
            List<Teretanatreneri> teretanatreneri = teretanaTreneriRepository.findAllByIdTeretane(idTeretane);
            List<Trener> treneri = new ArrayList<>();
            for (var x : teretanatreneri) {
                Optional<Trener> trener = trenerRepository.findById(x.getId().getIdtrenera());
                if (trener.isPresent()) {
                    treneri.add(trener.get());
                }
            }

            return treneri.stream()
                    .sorted((a, b) -> a.getPrezimetrenera().compareTo(b.getPrezimetrenera()))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Teretana ne postoji.");
            throw new IllegalArgumentException("Teretana ne postoji.");
        }
    }

    public void dodajTeretanu(TeretanaDTO teretanaDto) {
        Optional<Grad> grad = gradRepository.findById(teretanaDto.getPbr());
        Teretana teretana = new Teretana();
        if (grad.isPresent()) {
            teretana.setPbrgrada(grad.get());
        } else {
            teretana.setPbrgrada(null);
        }
        teretana.setImeteretane(teretanaDto.getIme());
        teretana.setAdresateretane(teretanaDto.getAdresa());
        teretana.setClanarinateretane(teretanaDto.getClanarina());
        teretana.setRadnovrijemeteretane(teretanaDto.getRadnoVrijeme());
        teretana.setOpisteretane(teretanaDto.getOpis());
        teretanaRepository.save(teretana);
        Optional<Teretana> spremljenaTeretana = teretanaRepository.findByImeteretane(teretanaDto.getIme());
        if (spremljenaTeretana.isPresent()) {
            Integer idTeretane = spremljenaTeretana.get().getId();
            Integer[] ideviTrenera = teretanaDto.getIdeviTrenera();
            for (int i = 0; i < ideviTrenera.length; i++) {
                if (trenerRepository.existsById(ideviTrenera[i])) {
                    teretanaTreneriRepository.save(new Teretanatreneri(new TeretanatreneriId(ideviTrenera[i], idTeretane)));
                }
            }
            return;
        }
        System.out.println("Greška u dodavanju teretane.");
        throw new IllegalArgumentException("Greška u dodavanju teretane.");
    }

    public void obrisiTeretanu(Integer id) {
        if (id != null) {
            teretanaRepository.deleteById(id);
        } else {
            System.out.println("Id ne postoji.");
            throw new IllegalArgumentException("Id ne postoji.");
        }
    }

    public TeretanaDTO getTeretana(Integer idTeretane) {
        Optional<Teretana> teretana = teretanaRepository.findById(idTeretane);
        if (teretana.isPresent()) {
            List<Teretanatreneri> teretanatreneri = teretanaTreneriRepository.findAllByIdTeretane(idTeretane);
            Integer[] polje = new Integer[teretanatreneri.size()];
            int i = 0;
            for (var t : teretanatreneri) {
                polje[i] = t.getId().getIdtrenera();
                i++;
            }
            TeretanaDTO teretanaDTO = new TeretanaDTO();
            teretanaDTO.setId(idTeretane);
            teretanaDTO.setIme(teretana.get().getImeteretane());
            teretanaDTO.setAdresa(teretana.get().getAdresateretane());
            teretanaDTO.setClanarina(teretana.get().getClanarinateretane());
            teretanaDTO.setRadnoVrijeme(teretana.get().getRadnovrijemeteretane());
            teretanaDTO.setOpis(teretana.get().getOpisteretane());
            teretanaDTO.setIdeviTrenera(polje);

            if (teretana.get().getPbrgrada() == null) {
                teretanaDTO.setImeGrada("Teretana trenutno nije vezana niti za jedan grad.");
            } else {
                teretanaDTO.setPbr(teretana.get().getPbrgrada().getPbr());
                teretanaDTO.setImeGrada(teretana.get().getPbrgrada().getImegrada());
            }
            return teretanaDTO;
        }
        System.out.println("Greška u dohvaćanju teretane.");
        throw new IllegalArgumentException("Greška u dohvaćanju teretane.");
    }


    public void urediTeretanu(Integer idTeretane, TeretanaDTO teretanaDto) {
        Optional<Grad> grad = gradRepository.findById(teretanaDto.getPbr());
        Teretana teretana = new Teretana();
        if (grad.isPresent()) {
            teretana.setPbrgrada(grad.get());
        } else {
            teretana.setPbrgrada(null);
        }
        teretana.setImeteretane(teretanaDto.getIme());
        teretana.setAdresateretane(teretanaDto.getAdresa());
        teretana.setClanarinateretane(teretanaDto.getClanarina());
        teretana.setRadnovrijemeteretane(teretanaDto.getRadnoVrijeme());
        teretana.setOpisteretane(teretanaDto.getOpis());
        teretana.setId(idTeretane);
        teretanaRepository.save(teretana);

        Integer[] ideviTrenera = teretanaDto.getIdeviTrenera();
        List<Teretanatreneri> list = teretanaTreneriRepository.findAll();
        for (var teretanaTreneri: list) {
            if (teretanaTreneri.getId().getIdteretane() == idTeretane) {
                teretanaTreneriRepository.deleteById(teretanaTreneri.getId());
            }
        }

        for (int i = 0; i < ideviTrenera.length; i++) {
            if (trenerRepository.existsById(ideviTrenera[i])) {
                teretanaTreneriRepository.save(new Teretanatreneri(new TeretanatreneriId(ideviTrenera[i], idTeretane)));
            }
        }

        return;
    }
}
