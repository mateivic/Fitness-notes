package com.example.FitnessNotes.model;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "klijent", indexes = {
        @Index(name = "klijent_mailklijenta_key", columnList = "mailklijenta", unique = true)
})
public class Klijent implements UserDetails {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idklijenta", nullable = false)
    private Integer id;

    @Column(name = "datumrodklijenta")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumrodklijenta;

    @Column(name = "imeklijenta", nullable = false, length = 50)
    private String imeklijenta;

    @Column(name = "prezimeklijenta", nullable = false, length = 50)
    private String prezimeklijenta;

    @Column(name = "mailklijenta", nullable = false, length = 50)
    private String mailklijenta;

    @Column(name = "lozinkaklijenta", nullable = false)
    private String lozinkaklijenta;

    @Column(name = "ulogaklijenta", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole uloga;


    @ManyToOne
    @JoinColumn(name = "idteretane")
    private Teretana idteretane;


//    @OneToMany(orphanRemoval = true)
//    @JoinColumn(name = "klijent_idklijenta")
//    private List<Trening> treninzi = new ArrayList<>();

    public Klijent(String imeklijenta, String prezimeklijenta, String mailklijenta, String lozinkaklijenta,
                   UserRole uloga) {
        this.imeklijenta = imeklijenta;
        this.prezimeklijenta = prezimeklijenta;
        this.mailklijenta = mailklijenta;
        this.lozinkaklijenta = lozinkaklijenta;
        this.uloga = uloga;
    }

    public UserRole getUloga() { return uloga; }

    public void setUloga(UserRole uloga) { this.uloga = uloga; }

//    public List<Trening> getTreninzi() { return treninzi; }
//
//    public void setTreninzi(List<Trening> treninzi) {
//        this.treninzi = treninzi;
//    }

    public Teretana getIdteretane() {
        return idteretane;
    }

    public void setIdteretane(Teretana idteretane) {
        this.idteretane = idteretane;
    }

    public String getLozinkaklijenta() {
        return lozinkaklijenta;
    }

    public void setLozinkaklijenta(String lozinkaklijenta) {
        this.lozinkaklijenta = lozinkaklijenta;
    }

    public String getMailklijenta() {
        return mailklijenta;
    }

    public void setMailklijenta(String mailklijenta) {
        this.mailklijenta = mailklijenta;
    }

    public String getPrezimeklijenta() {
        return prezimeklijenta;
    }

    public void setPrezimeklijenta(String prezimeklijenta) {
        this.prezimeklijenta = prezimeklijenta;
    }

    public String getImeklijenta() {
        return imeklijenta;
    }

    public void setImeklijenta(String imeklijenta) {
        this.imeklijenta = imeklijenta;
    }

    public LocalDate getDatumrodklijenta() {
        return datumrodklijenta;
    }

    public void setDatumrodklijenta(LocalDate datumrodklijenta) {
        this.datumrodklijenta = datumrodklijenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.uloga.name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.lozinkaklijenta;
    }

    @Override
    public String getUsername() {
        return this.mailklijenta;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}