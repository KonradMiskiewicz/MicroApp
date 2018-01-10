package com.example.games;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * Entity dla tabeli games
 * idgame - id gry
 * name - nazwa gry
 * category - kategoria gry
 * price - cena gry
 * releaseDate - data wydania gry
 */
@Entity
public class Games {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idgame;
	
	@Column(name = "name", nullable = false, length = 255)
    private String name;
	
	@Column(name = "category", nullable = false, length = 255)
	private String category;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "rel", nullable = false)
	private Date releaseDate;

	public Long getIdgame() {
		return idgame;
	}

	public void setIdgame(Long idgame) {
		this.idgame = idgame;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
