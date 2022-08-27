package com.example.FitnessNotes.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginDTO {
    private String email;
    private String lozinka;
}
