package com.example.FitnessNotes.DTO;

import com.example.FitnessNotes.model.Trening;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TreningIVjezbeDTO {
    Trening trening;
    List<VjezbaUTreninguDTO> vjezbeUTreningu;
}
