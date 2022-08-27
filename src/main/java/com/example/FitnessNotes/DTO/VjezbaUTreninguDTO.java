package com.example.FitnessNotes.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class VjezbaUTreninguDTO {
    Integer idVjezbe;
    String imeVjezbe;
    Integer brojSetova;
    Integer brojPonavljanja;
    Integer opterecenje;
    Integer rpe;
}
