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
@Table(name = "rekord")
public class Rekord {
    @EmbeddedId
    private RekordId id;

    @Column(name = "onerepmax", nullable = false)
    private Integer onerepmax;

    public Integer getOnerepmax() {
        return onerepmax;
    }

    public void setOnerepmax(Integer onerepmax) {
        this.onerepmax = onerepmax;
    }

    public RekordId getId() {
        return id;
    }

    public void setId(RekordId id) {
        this.id = id;
    }
}