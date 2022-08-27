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
public class TreningDTO {
    private Integer idKlijenta;
    private Integer trajanje;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
}
