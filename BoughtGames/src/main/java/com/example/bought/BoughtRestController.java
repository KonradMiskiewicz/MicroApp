package com.example.bought;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Kontroler dla mikroUslugi do kupionych gier
 *
 */
@RestController
@RequestMapping("/bought")
public class BoughtRestController {

	@Autowired
	BoughtRepository boughtRepo;
	
	/**
	 * Wyswietlenie gier kupionych przez usera
	 * @param id - id usera
	 * @return kupione gry
	 */
	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Bought> userGames(@PathVariable("id") Long id) {
		
		return boughtRepo.findBoughtByIduser(id);
	}
	
	/**
	 * Dodanie gry do kupionych
	 * @param id - id usera kupujacego
	 * @param idgame - id gry ktora user kupuje
	 * @return
	 */
	@RequestMapping(path = "/add/{id}/{idgame}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Bought addGame(@PathVariable("id") Long id, @PathVariable("idgame") Long idgame) {
		Bought bought = new Bought();
		
		bought.setIduser(id);
		
		bought.setIdgame(idgame);
		
		boughtRepo.save(bought);
		
		return bought;
	}
	
	/**
	 * Sprawdzenie czy gra zostala kupiona
	 * @param id - id aktywnego usera
	 * @param idgame - id sprawdzanej gry
	 * @return
	 */
	@RequestMapping(path = "/check/{id}/{idgame}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Bought checkStatus(@PathVariable("id") Long id, @PathVariable("idgame") Long idgame) {
		
		return boughtRepo.findByIduserAndIdgame(id, idgame);
	}
}
