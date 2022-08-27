package com.example.FitnessNotes.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TeretanaDTO {
    private Integer id;
    private String ime;
    private String adresa;
    private Integer clanarina;
    private String radnoVrijeme;
    private String opis;
    private Integer pbr;
    private String imeGrada;
    private Integer[] ideviTrenera;
}
