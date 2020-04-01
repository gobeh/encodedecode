package com.example.encdec.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Peserta {
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(strategy = "uuid2", name = "uuid")
    private String id;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 3, max =150)
    private String nama;

    @Column(nullable = false,unique = true)
    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Column(nullable = false, name = "tanggal_lahir")
    @Temporal(TemporalType.DATE)
    @NotNull
    @Past
    private Date tanggalLahir;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
