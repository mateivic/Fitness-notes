package com.example.FitnessNotes.model;

import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Table(name = "teretana", indexes = {
        @Index(name = "teretana_imeteretane_key", columnList = "imeteretane", unique = true)
})
public class Teretana {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idteretane", nullable = false)
    private Integer id;

    @Column(name = "adresateretane", nullable = false, length = 100)
    private String adresateretane;

    @Column(name = "imeteretane", nullable = false, length = 100)
    private String imeteretane;

    @Column(name = "clanarinateretane", nullable = false)
    private Integer clanarinateretane;

    @Column(name = "radnovrijemeteretane", nullable = false, length = 50)
    private String radnovrijemeteretane;

    @Column(name = "opisteretane", length = 500)
    private String opisteretane;

    @ManyToOne
    @JoinColumn(name = "pbrgrada")
    private Grad pbrgrada;

    public Grad getPbrgrada() {
        return pbrgrada;
    }

    public void setPbrgrada(Grad pbrgrada) {
        this.pbrgrada = pbrgrada;
    }

    public String getOpisteretane() {
        return opisteretane;
    }

    public void setOpisteretane(String opisteretane) {
        this.opisteretane = opisteretane;
    }

    public String getRadnovrijemeteretane() {
        return radnovrijemeteretane;
    }

    public void setRadnovrijemeteretane(String radnovrijemeteretane) {
        this.radnovrijemeteretane = radnovrijemeteretane;
    }

    public Integer getClanarinateretane() {
        return clanarinateretane;
    }

    public void setClanarinateretane(Integer clanarinateretane) {
        this.clanarinateretane = clanarinateretane;
    }

    public String getImeteretane() {
        return imeteretane;
    }

    public void setImeteretane(String imeteretane) {
        this.imeteretane = imeteretane;
    }

    public String getAdresateretane() {
        return adresateretane;
    }

    public void setAdresateretane(String adresateretane) {
        this.adresateretane = adresateretane;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}