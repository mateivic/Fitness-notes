package com.example.FitnessNotes.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "drzava")
public class Drzava {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "iddrzave", nullable = false)
    private Integer id;

    @Column(name = "imedrzave", nullable = false)
    private String imeDrzave;

//    @OneToMany(orphanRemoval = true)
//    @JoinColumn(name = "drzava_iddrzave")
//    private List<Grad> gradovi = new ArrayList<>();

    public Drzava(String imeDrzave) {
        this.imeDrzave = imeDrzave;
    }

//    public List<Grad> getGradovi() {
//        return gradovi;
//    }
//
//    public void setGradovi(List<Grad> gradovi) {
//        this.gradovi = gradovi;
//    }

    public String getImeDrzave() {
        return imeDrzave;
    }

    public void setImeDrzave(String imeDrzave) {
        this.imeDrzave = imeDrzave;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}