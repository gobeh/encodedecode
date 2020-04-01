package com.example.encdec.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(schema = "public", name = "s_user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;

    @NotEmpty(message = "Username tidak boleh kosong")
    @Column(name = "username", length = 30)
    private String username;

    @NotNull
    @NotEmpty
    @Column(name = "password")
    private String password;


    @NotEmpty(message = "Nama tidak boleh kosong")
    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "status")
    private Boolean status = false;

}
