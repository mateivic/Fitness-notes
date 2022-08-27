package com.example.FitnessNotes.model;

import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@ToString
@Embeddable
public class VjezbeutreninguId implements Serializable {
    private static final long serialVersionUID = 3249656005984261292L;
    @Column(name = "idtreninga", nullable = false)
    private Integer idtreninga;
    @Column(name = "idvjezbe", nullable = false)
    private Integer idvjezbe;

    public Integer getIdvjezbe() {
        return idvjezbe;
    }

    public void setIdvjezbe(Integer idvjezbe) {
        this.idvjezbe = idvjezbe;
    }

    public Integer getIdtreninga() {
        return idtreninga;
    }

    public void setIdtreninga(Integer idtreninga) {
        this.idtreninga = idtreninga;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtreninga, idvjezbe);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VjezbeutreninguId entity = (VjezbeutreninguId) o;
        return Objects.equals(this.idtreninga, entity.idtreninga) &&
                Objects.equals(this.idvjezbe, entity.idvjezbe);
    }
}