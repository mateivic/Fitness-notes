package com.example.FitnessNotes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TeretanatreneriId implements Serializable {
    private static final long serialVersionUID = 1840563288522710879L;
    @Column(name = "idtrenera", nullable = false)
    private Integer idtrenera;
    @Column(name = "idteretane", nullable = false)
    private Integer idteretane;

    public Integer getIdteretane() {
        return idteretane;
    }

    public void setIdteretane(Integer idteretane) {
        this.idteretane = idteretane;
    }

    public Integer getIdtrenera() {
        return idtrenera;
    }

    public void setIdtrenera(Integer idtrenera) {
        this.idtrenera = idtrenera;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idteretane, idtrenera);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TeretanatreneriId entity = (TeretanatreneriId) o;
        return Objects.equals(this.idteretane, entity.idteretane) &&
                Objects.equals(this.idtrenera, entity.idtrenera);
    }
}