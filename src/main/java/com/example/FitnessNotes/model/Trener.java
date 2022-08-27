package com.example.FitnessNotes.model;

import javax.persistence.*;

@Entity
@Table(name = "trener")
public class Trener {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idtrenera", nullable = false)
    private Integer id;

    @Column(name = "igprofiltrenera", length = 50)
    private String igprofiltrenera;

    @Column(name = "mobiteltrenera", length = 20)
    private String mobiteltrenera;

    @Column(name = "slikatrenera")
    private String slikatrenera;

    @Column(name = "cijenatrenera", nullable = false)
    private Integer cijenatrenera;

    @Column(name = "imetrenera", nullable = false, length = 50)
    private String imetrenera;

    @Column(name = "prezimetrenera", nullable = false, length = 50)
    private String prezimetrenera;

    public String getPrezimetrenera() {
        return prezimetrenera;
    }

    public void setPrezimetrenera(String prezimetrenera) {
        this.prezimetrenera = prezimetrenera;
    }

    public String getImetrenera() {
        return imetrenera;
    }

    public void setImetrenera(String imetrenera) {
        this.imetrenera = imetrenera;
    }

    public Integer getCijenatrenera() {
        return cijenatrenera;
    }

    public void setCijenatrenera(Integer cijenatrenera) {
        this.cijenatrenera = cijenatrenera;
    }

    public String getSlikatrenera() {
        return slikatrenera;
    }

    public void setSlikatrenera(String slikatrenera) {
        this.slikatrenera = slikatrenera;
    }

    public String getMobiteltrenera() {
        return mobiteltrenera;
    }

    public void setMobiteltrenera(String mobiteltrenera) {
        this.mobiteltrenera = mobiteltrenera;
    }

    public String getIgprofiltrenera() {
        return igprofiltrenera;
    }

    public void setIgprofiltrenera(String igprofiltrenera) {
        this.igprofiltrenera = igprofiltrenera;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}