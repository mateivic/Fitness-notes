package com.example.FitnessNotes.DTO;

import com.example.FitnessNotes.model.Rekord;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RekordDTO {
    private Integer id;
    private Rekord rekord;
    private String imeVjezbe;
}
