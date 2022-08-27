package com.example.FitnessNotes.DTO;

import com.example.FitnessNotes.model.Misicnagrupa;
import com.example.FitnessNotes.model.Vjezba;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MisicnaGrupaIVjezbeDTO {
    private Misicnagrupa misicnagrupa;
    private List<Vjezba> vjezbe;

}
