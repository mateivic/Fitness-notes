package com.example.FitnessNotes.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OsobniPodaciDTO {
    private String ime;
    private String prezime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumRod;
    private Integer teretanaId;
    private String imeTeretane;

}
