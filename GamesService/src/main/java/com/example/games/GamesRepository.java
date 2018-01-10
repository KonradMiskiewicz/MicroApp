package com.example.games;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * Repozytorium dla tabeli games
 *
 */
public interface GamesRepository extends JpaRepository<Games, Long> {
	// Pobiera 4 najnowsze gry z DB
	public List<Games> findTop4ByOrderByReleaseDateDesc();
	
	// pobiera gre po jej id
	public Games findGamesByIdgame(Long idgame);
	
	// pobiera gry po kategorii
	public List<Games> findGamesByCategory(String category);
}