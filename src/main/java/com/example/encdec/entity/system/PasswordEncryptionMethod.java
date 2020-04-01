/* Copyright (C) Syarif Hidayatullah State Islamic University Jakarta, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PUSTIPANDA <pustipanda@uinjkt.ac.id>, December 2015
 */

package com.example.encdec.entity.system;

// Generated Apr 16, 2010 2:27:16 PM by Hibernate Tools 3.2.4.CR1

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(schema = "public", name = "password_encryption_method")
public class PasswordEncryptionMethod implements Serializable {
	private static final long serialVersionUID = 2463821577548439808L;
	private Long id;
	private String namaMetode;
	private String kunci;
	private Boolean aktif;

	private String oleh;
	private Date tanggal_dirubah = new Date();

	public String toString() {
		return namaMetode;
	}

	public PasswordEncryptionMethod() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", insertable = false, unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nama_metode", nullable = false, length = 255)
	public String getNamaMetode() {
		return this.namaMetode;
	}

	public void setNamaMetode(String namaMetode) {
		this.namaMetode = namaMetode;
	}

	@Column(name = "kunci", nullable = false)
	public String getKunci() {
		return this.kunci;
	}

	public void setKunci(String kunci) {
		this.kunci = kunci;
	}

	@Column(name = "aktif", nullable = false)
	public Boolean getAktif() {
		return aktif;
	}

	public void setAktif(Boolean aktif) {
		this.aktif = aktif;
	}

	public void setOleh(String oleh) {
		this.oleh = oleh;
	}

	public String getOleh() {
		return oleh;
	}

	public void setTanggal_dirubah(Date tanggal_dirubah) {
		this.tanggal_dirubah = tanggal_dirubah;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getTanggal_dirubah() {
		return tanggal_dirubah;
	}

}
