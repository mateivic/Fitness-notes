package com.example.FitnessNotes.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "grad")
public class Grad {

    @Id
    @Column(name = "pbrgrada", nullable = false)
    private Integer pbr;

    @Column(name = "imegrada", nullable = false, length = 50)
    private String imegrada;

    @ManyToOne
    @JoinColumn(name = "iddrzave")
    private Drzava iddrzave;

//    @OneToMany(orphanRemoval = true)
//    @JoinColumn(name = "grad_pbrgrada")
//    private List<Teretana> teretane = new ArrayList<>();

    public Grad(Integer pbr, String imegrada, Drzava iddrzave) {
        this.pbr = pbr;
        this.imegrada = imegrada;
        this.iddrzave = iddrzave;
    }
//    public List<Teretana> getTeretane() {
//        return teretane;
//    }
//
//    public void setTeretane(List<Teretana> teretane) {
//        this.teretane = teretane;
//    }

    public Drzava getIddrzave() {
        return iddrzave;
    }

    public void setIddrzave(Drzava iddrzave) {
        this.iddrzave = iddrzave;
    }

    public String getImegrada() {
        return imegrada;
    }

    public void setImegrada(String imegrada) {
        this.imegrada = imegrada;
    }

    public Integer getPbr() {
        return pbr;
    }

    public void setPbr(Integer pbr) {
        this.pbr = pbr;
    }
}