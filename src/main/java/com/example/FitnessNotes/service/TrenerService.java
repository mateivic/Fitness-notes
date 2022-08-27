package com.example.FitnessNotes.service;

import com.example.FitnessNotes.DTO.TrenerDTO;
import com.example.FitnessNotes.model.Teretanatreneri;
import com.example.FitnessNotes.model.TeretanatreneriId;
import com.example.FitnessNotes.model.Trener;
import com.example.FitnessNotes.repository.TeretanaRepository;
import com.example.FitnessNotes.repository.TeretanaTreneriRepository;
import com.example.FitnessNotes.repository.TrenerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrenerService {
    TrenerRepository trenerRepository;
    TeretanaTreneriRepository teretanaTreneriRepository;
    TeretanaRepository teretanaRepository;

    public String ucitajSliku(MultipartFile slika) {
        if (!slika.isEmpty()) {
            String fileName = StringUtils.cleanPath(slika.getOriginalFilename());
            try {
                Path path = Paths.get("C:\\Users\\PC\\OneDrive\\Dokumenti\\Faks\\Zavrsni rad\\FitnessNotes\\src\\main\\resources\\static\\images", fileName );
                Files.copy(slika.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                return "/images/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void dodajTrenera(TrenerDTO trenerDTO) {
        Trener trener = new Trener();
        trener.setImetrenera(trenerDTO.getIme());
        trener.setPrezimetrenera(trenerDTO.getPrezime());
        trener.setCijenatrenera(trenerDTO.getCijena());
        trener.setSlikatrenera(trenerDTO.getSlikaString());
        trener.setMobiteltrenera(trenerDTO.getMobitel());
        trener.setIgprofiltrenera(trenerDTO.getInstagram());
        trener = trenerRepository.save(trener);

        for (int i = 0; i < trenerDTO.getIdviTeretana().length; i++) {
            if(teretanaRepository.existsById(trenerDTO.getIdviTeretana()[i])) {
                teretanaTreneriRepository.save(new Teretanatreneri
                        (new TeretanatreneriId(trener.getId(), trenerDTO.getIdviTeretana()[i])));
            }
        }
        return;
    }

    public TrenerDTO getTrenera(Integer id) {
        Optional<Trener> trener = trenerRepository.findById(id);
        if (trener.isPresent()) {
            TrenerDTO trenerDTO = new TrenerDTO();
            trenerDTO.setId(trener.get().getId());
            trenerDTO.setIme(trener.get().getImetrenera());
            trenerDTO.setPrezime(trener.get().getPrezimetrenera());
            trenerDTO.setCijena(trener.get().getCijenatrenera());
            trenerDTO.setSlikaString(trener.get().getSlikatrenera());
            trenerDTO.setMobitel(trener.get().getMobiteltrenera());
            trenerDTO.setInstagram(trener.get().getIgprofiltrenera());

            List<Teretanatreneri> list = teretanaTreneriRepository.findAllByIdTrenera(id);
            Integer[] array = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i).getId().getIdteretane();
            }
            trenerDTO.setIdviTeretana(array);
            return trenerDTO;
        } else {
            System.out.println("Trener ne postoji");
            throw new IllegalArgumentException("Trener ne postoji");
        }
    }

    public void urediTrenera(TrenerDTO trenerDTO, Integer idTrenera) {
        Trener trener = new Trener();
        if (trenerDTO.getSlika() != null) {
            trener.setSlikatrenera(ucitajSliku(trenerDTO.getSlika()));
        } else {
            trener.setSlikatrenera(trenerDTO.getSlikaString());
        }
        trener.setImetrenera(trenerDTO.getIme());
        trener.setPrezimetrenera(trenerDTO.getPrezime());
        trener.setCijenatrenera(trenerDTO.getCijena());
        trener.setMobiteltrenera(trenerDTO.getMobitel());
        trener.setIgprofiltrenera(trenerDTO.getInstagram());
        trener.setId(idTrenera);
        trenerRepository.save(trener);

        Integer[] ideviTeretana = trenerDTO.getIdviTeretana();
        List<Teretanatreneri> list = teretanaTreneriRepository.findAll();
        for (var teretanaTreneri: list) {
            if (teretanaTreneri.getId().getIdtrenera() == idTrenera) {
                teretanaTreneriRepository.deleteById(teretanaTreneri.getId());
            }
        }
        for (int i = 0; i < ideviTeretana.length; i++) {
            if (teretanaRepository.existsById(ideviTeretana[i])) {
                teretanaTreneriRepository.save(new Teretanatreneri(new TeretanatreneriId(idTrenera, ideviTeretana[i])));
            }
        }

        return;
    }

    public void obrisiTrenera(Integer id) {
        if (id != null) {
            trenerRepository.deleteById(id);
        } else {
            System.out.println("Id ne postoji.");
            throw new IllegalArgumentException("Id ne postoji.");
        }
    }
}
