package com.example.FitnessNotes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teretanatreneri")
public class Teretanatreneri {
    @EmbeddedId
    private TeretanatreneriId id;

    public TeretanatreneriId getId() {
        return id;
    }

    public void setId(TeretanatreneriId id) {
        this.id = id;
    }
}