package com.example.games;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/games")
public class GamesRestController {

	@Autowired
	GamesRepository gamesRepo;
	
	/**
	 * Pobiera 4 najnowsze gry
	 * @return
	 */
	@RequestMapping(path = "/newGames", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Games> getNewGames() {
		Iterable<Games> games = gamesRepo.findTop4ByOrderByReleaseDateDesc();
		List<Games> gamesConvert = new ArrayList<Games>();
		games.forEach(gamesConvert::add);
		return gamesConvert;
	}
	
	/**
	 * Pobiera informacje o grze
	 * @param idgame - id gry ktorej chcemy informacje
	 * @return
	 */
	@RequestMapping(path = "/info/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Games getInfoGame(@PathVariable("id") Long idgame) {
		Games game = gamesRepo.findGamesByIdgame(idgame);
		return game;
	}
	
	/**
	 * Pobranie gier po kategorii
	 * @param category - kategoria gier do pobrania
	 * @return
	 */
	@RequestMapping(path = "/category/{cat}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Games> getGamesThroughCat(@PathVariable("cat") String category) {
		Iterable<Games> games = gamesRepo.findGamesByCategory(category);
		List<Games> gamesConvert = new ArrayList<Games>();
		games.forEach(gamesConvert::add);
	
		return gamesConvert;
	}
}
