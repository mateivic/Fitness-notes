package com.example.FitnessNotes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mjere")
public class Mjere {
    @EmbeddedId
    private MjereId id;

    @Column(name = "visina", nullable = false)
    private Integer visina;

    @Column(name = "tezina", nullable = false)
    private Double tezina;

    @Column(name = "opsegstruka")
    private Double opsegstruka;

    @Column(name = "opsegprsa")
    private Double opsegprsa;

    @Column(name = "opsegruke")
    private Double opsegruke;

    public Double getOpsegruke() {
        return opsegruke;
    }

    public void setOpsegruke(Double opsegruke) {
        this.opsegruke = opsegruke;
    }

    public Double getOpsegprsa() {
        return opsegprsa;
    }

    public void setOpsegprsa(Double opsegprsa) {
        this.opsegprsa = opsegprsa;
    }

    public Double getOpsegstruka() {
        return opsegstruka;
    }

    public void setOpsegstruka(Double opsegstruka) {
        this.opsegstruka = opsegstruka;
    }

    public Double getTezina() {
        return tezina;
    }

    public void setTezina(Double tezina) {
        this.tezina = tezina;
    }

    public Integer getVisina() {
        return visina;
    }

    public void setVisina(Integer visina) {
        this.visina = visina;
    }

    public MjereId getId() {
        return id;
    }

    public void setId(MjereId id) {
        this.id = id;
    }
}