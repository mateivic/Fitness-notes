package com.example.FitnessNotes.model;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trening")
@NoArgsConstructor
public class Trening {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idtreninga", nullable = false)
    private Integer id;

    @Column(name = "datumtreninga", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumtreninga;

    @Column(name = "trajanjetreninga", nullable = false)
    private Integer trajanjetreninga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idklijenta", nullable = false)
    private Klijent idklijenta;

    public Trening(LocalDate datumtreninga, Integer trajanjetreninga, Klijent idklijenta) {
        this.datumtreninga = datumtreninga;
        this.trajanjetreninga = trajanjetreninga;
        this.idklijenta = idklijenta;
    }

    public Klijent getIdklijenta() {
        return idklijenta;
    }

    public void setIdklijenta(Klijent idklijenta) {
        this.idklijenta = idklijenta;
    }

    public Integer getTrajanjetreninga() {
        return trajanjetreninga;
    }

    public void setTrajanjetreninga(Integer trajanjetreninga) {
        this.trajanjetreninga = trajanjetreninga;
    }

    public LocalDate getDatumtreninga() {
        return datumtreninga;
    }

    public void setDatumtreninga(LocalDate datumtreninga) {
        this.datumtreninga = datumtreninga;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}