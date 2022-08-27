package com.example.FitnessNotes.model;

import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "vjezba")
public class Vjezba {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idvjezbe", nullable = false)
    private Integer id;

    @Column(name = "opisvjezbe", length = 500)
    private String opisvjezbe;

    @Column(name = "imevjezbe", nullable = false, length = 50)
    private String imevjezbe;

    @ManyToOne
    @JoinColumn(name = "idmisicnegrupe")
    private Misicnagrupa idmisicnegrupe;

    public Misicnagrupa getIdmisicnegrupe() {
        return idmisicnegrupe;
    }

    public void setIdmisicnegrupe(Misicnagrupa idmisicnegrupe) {
        this.idmisicnegrupe = idmisicnegrupe;
    }

    public String getImevjezbe() {
        return imevjezbe;
    }

    public void setImevjezbe(String imevjezbe) {
        this.imevjezbe = imevjezbe;
    }

    public String getOpisvjezbe() {
        return opisvjezbe;
    }

    public void setOpisvjezbe(String opisvjezbe) {
        this.opisvjezbe = opisvjezbe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}