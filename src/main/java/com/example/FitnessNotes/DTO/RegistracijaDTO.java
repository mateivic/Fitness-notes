package com.example.FitnessNotes.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class RegistracijaDTO {
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private String ponovljenaLozinka;

}
