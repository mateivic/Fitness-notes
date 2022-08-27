package com.example.FitnessNotes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RekordId implements Serializable {
    private static final long serialVersionUID = -8287511208075229934L;
    @Column(name = "datumrekorda", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumrekorda;
    @Column(name = "idklijenta", nullable = false)
    private Integer idklijenta;
    @Column(name = "idvjezbe", nullable = false)
    private Integer idvjezbe;

    public Integer getIdvjezbe() {
        return idvjezbe;
    }

    public void setIdvjezbe(Integer idvjezbe) {
        this.idvjezbe = idvjezbe;
    }

    public Integer getIdklijenta() {
        return idklijenta;
    }

    public void setIdklijenta(Integer idklijenta) {
        this.idklijenta = idklijenta;
    }

    public LocalDate getDatumrekorda() {
        return datumrekorda;
    }

    public void setDatumrekorda(LocalDate datumrekorda) {
        this.datumrekorda = datumrekorda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idklijenta, datumrekorda, idvjezbe);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RekordId entity = (RekordId) o;
        return Objects.equals(this.idklijenta, entity.idklijenta) &&
                Objects.equals(this.datumrekorda, entity.datumrekorda) &&
                Objects.equals(this.idvjezbe, entity.idvjezbe);
    }
}