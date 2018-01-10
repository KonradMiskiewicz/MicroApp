package com.example.bought;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * Klasa Entity dla polaczenia z baza danych
 * z tabela bought
 * ktora pokazuje kupione przez usera gry
 */
@Entity
public class Bought {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "iduser", nullable = false)
	private Long iduser;
	
	@Column(name = "idgame", nullable = false)
	private Long idgame;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIduser() {
		return iduser;
	}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	public Long getIdgame() {
		return idgame;
	}

	public void setIdgame(Long idgame) {
		this.idgame = idgame;
	}
	
	
}
