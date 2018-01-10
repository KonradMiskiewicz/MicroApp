package com.example.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Restowy Controller dla obslugi ajaxa dla kupowania gier
 *
 */
@RestController
@RequestMapping("/make")
public class UserRestController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	HttpSession session;
	
	/**
	 * Kupienie gry przez usera
	 * @param idgame - odpowiednia gra
	 * @param money - ilosc pieniedzy na koncie po kupieniu gry
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(path = "/buy/{idgame}/{money:.+}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Object buyGame(@PathVariable("idgame") Long idgame, @PathVariable("money") Double money) {
		
		// pobranie usera z sesji
		User user = (User)session.getAttribute("savedUser");
		
		// ustawienia konta po kupieniu gry
		Object acc = userService.setAccount(user.getId(), money);

		// dodania gry do kupionych
		Object bought = userService.addGame(user.getId(), idgame);
		
		return user;
	}
}