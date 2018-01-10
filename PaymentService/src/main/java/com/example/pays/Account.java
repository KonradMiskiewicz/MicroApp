package com.example.pays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * Klasa dla tabale accounts
 * idaccount id konta usera
 * balance kwota pieniezna usera
 */
@Entity
public class Account {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	private Long idaccount;

	@Column(name = "balance", nullable = false)
	private Double balance;

	public Long getIdaccount() {
		return idaccount;
	}

	public void setIdaccount(Long idaccount) {
		this.idaccount = idaccount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
}
