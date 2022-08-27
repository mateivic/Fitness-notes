package com.example.FitnessNotes.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Table(name = "misicnagrupa")
public class Misicnagrupa {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idmisicnegrupe", nullable = false)
    private Integer id;

    @Column(name = "imemisicnegrupe", nullable = false, length = 100)
    private String imemisicnegrupe;

    @Column(name = "slikamisicnegrupe")
    private String slikamisicnegrupe;

//    @OneToMany(orphanRemoval = true)
//    @JoinColumn(name = "misicnagrupa_idmisicnegrupe")
//    private List<Vjezba> vjezbe = new ArrayList<>();

//    public List<Vjezba> getVjezbe() {
//        return vjezbe;
//    }
//
//    public void setVjezbe(List<Vjezba> vjezbe) {
//        this.vjezbe = vjezbe;
//    }

    public String getSlikamisicnegrupe() {
        return slikamisicnegrupe;
    }

    public void setSlikamisicnegrupe(String slikamisicnegrupe) {
        this.slikamisicnegrupe = slikamisicnegrupe;
    }

    public String getImemisicnegrupe() {
        return imemisicnegrupe;
    }

    public void setImemisicnegrupe(String imemisicnegrupe) {
        this.imemisicnegrupe = imemisicnegrupe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}