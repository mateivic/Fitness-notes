package com.example.FitnessNotes.model;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@Entity
@Table(name = "vjezbeutreningu")
public class Vjezbeutreningu {
    @EmbeddedId
    private VjezbeutreninguId id;

    @Column(name = "opterecenje", nullable = false)
    private Integer opterecenje;

    @Column(name = "ponavljanja", nullable = false)
    private Integer ponavljanja;

    @Column(name = "rpe")
    private Integer rpe;

    @Column(name = "brojsetova", nullable = false)
    private Integer brojsetova;

    public Integer getBrojsetova() {
        return brojsetova;
    }

    public void setBrojsetova(Integer brojsetova) {
        this.brojsetova = brojsetova;
    }

    public Integer getRpe() {
        return rpe;
    }

    public void setRpe(Integer rpe) {
        this.rpe = rpe;
    }

    public Integer getPonavljanja() {
        return ponavljanja;
    }

    public void setPonavljanja(Integer ponavljanja) {
        this.ponavljanja = ponavljanja;
    }

    public Integer getOpterecenje() {
        return opterecenje;
    }

    public void setOpterecenje(Integer opterecenje) {
        this.opterecenje = opterecenje;
    }

    public VjezbeutreninguId getId() {
        return id;
    }

    public void setId(VjezbeutreninguId id) {
        this.id = id;
    }
}