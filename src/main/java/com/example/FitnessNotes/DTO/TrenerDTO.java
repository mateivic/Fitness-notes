package com.example.FitnessNotes.DTO;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TrenerDTO {
    private Integer id;
    private String ime;
    private String prezime;
    private Integer cijena;
    private MultipartFile slika;
    private String slikaString;
    private String mobitel;
    private String instagram;
    Integer[] idviTeretana;

}
