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
public class MjeraDTO {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
    private Integer visina;
    private Double tezina;
    private Double opsegStruka;
    private Double opsegPrsa;
    private Double opsegRuke;
}
