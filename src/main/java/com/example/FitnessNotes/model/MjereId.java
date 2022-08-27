package com.example.FitnessNotes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MjereId implements Serializable {
    private static final long serialVersionUID = -764665788117469100L;
    @Column(name = "datummjerenja", nullable = false)
    private LocalDate datummjerenja;
    @Column(name = "idklijenta", nullable = false)
    private Integer idklijenta;

    public Integer getIdklijenta() {
        return idklijenta;
    }

    public void setIdklijenta(Integer idklijenta) {
        this.idklijenta = idklijenta;
    }

    public LocalDate getDatummjerenja() {
        return datummjerenja;
    }

    public void setDatummjerenja(LocalDate datummjerenja) {
        this.datummjerenja = datummjerenja;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idklijenta, datummjerenja);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MjereId entity = (MjereId) o;
        return Objects.equals(this.idklijenta, entity.idklijenta) &&
                Objects.equals(this.datummjerenja, entity.datummjerenja);
    }
}