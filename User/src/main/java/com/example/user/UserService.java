package com.example.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * Klasa service dla polaczen z microuslugami
 *
 */
@Service
public class UserService {

	// Pobranie nowych gier
	public List getNewGames() {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/game/games/newGames", List.class);
	}
	
	// Pobranie informacji o grze
	public Object getInfoGame(Long id) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/game/games/info/" + id, Object.class);
	}
	
	// Ustawienie konta usera
	public Object setAccount(Long id, Double money) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/account/account/set/" + id + "/" + money, Object.class);
	}
	
	// Pobranie konta usera
	public Object getAccount(Long id) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/account/account/getMoney/" + id, Object.class);
	}
	
	// Dodanie gry do kupionych przez usera
	public Object addGame(Long id, Long idgame) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/bought/bought/add/" + id + "/" + idgame, Object.class);
	}
	
	// Pobranie kupionych gier przez usera
	public List getBoughtGames(Long id) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/bought/bought/get/" + id, List.class);
	}
	
	// Sprawdzenie czy gra zostala kupiona przez usera
	public Object checkBoughtStatus(Long id, Long idgame) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/bought/bought/check/" + id + "/" + idgame, Object.class);
	}
	
	public List getGamesCat(String category) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:8765/api/game/games/category/" + category, List.class);
	}
}
