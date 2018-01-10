package com.example.bought;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * Repozytorium dla polaczenia z baza danych
 *
 */
public interface BoughtRepository extends CrudRepository<Bought, String>{

	// Znalezieniu gier kupionych przez usera
	List<Bought> findBoughtByIduser(Long iduser);
	
	// Znalezienie czy konkretna gra zostalo kupiona przez usera
	Bought findByIduserAndIdgame(Long iduser, Long idgame);
}
