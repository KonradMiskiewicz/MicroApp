package com.example.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Controller MVC dla clienta usera
 *
 */
@Controller
@RequestMapping("/userService")
public class UserNormalController {

	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	HttpSession session;
	
	/**
	 * Glowna strona
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {
		session = request.getSession();
		model.addAttribute("user", new User());
		return "mainSite/index";
	}
	
	/**
	 * Register form
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "register-view";
	}
	
	/**
	 * Handle register form request
	 * @param user
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/register")
	public String register(@Valid User user, BindingResult bindingResult, Model model) {
		// Walidacja danych z forma
		if (bindingResult.hasErrors()) {
			return "register-view";
		}
		
		try {
			
			// haslo przed hashowaniem
			String passwordBeforeHashing = user.getPassword();
			
			// hashowanie hasla
		    user.setPassword(new BCryptPasswordEncoder().encode(passwordBeforeHashing));
		    
		    // zapisanie usera do DB
		    userRepo.save(user);
		} catch (Exception e) {
			// zwrocenie bledu i powrot do forma
			bindingResult.rejectValue("email", "email.notvalid", "Email was used");
			
			return "register-view";
		}
		
		// dodanie usera do sesji
		session.setAttribute("savedUser", user);
	
		// stworzenie userowi konta z balancem 0
		Object acc = userService.setAccount(user.getId(), 0.00);
		
		// jezeli wszystko jest ok redirect na glowna strone po rejestracji/logowaniu
		if (acc != null) 
		    return "redirect:http://localhost:8080/userService/mainPage";
		else {
			bindingResult.rejectValue("email", "email.notvalid", "Something went wrong. Try again");
			
			return "register-view";
		}
	}
	
	/**
	 * Login form
	 * @param model
	 * @return
	 */
	@GetMapping("/login")
	public String getLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "login-view";
	}
	
	/**
	 * Handle login form request
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/login")
	public String login(@Valid User user, BindingResult bindingResult) {
		// sprawdzenie czy dane sa zgodne
		if (bindingResult.hasErrors()) {
			return "login-view";
		}
		
		// Pobranie usera z bazy danych po emailu
	    User userFromDB = userRepo.findByEmail(user.getEmail());
	    
	    // Jezeli nie znaloziono usera to powrot z bledem do forma z logowaniem
	    if (userFromDB == null) {
	    	
	    	bindingResult.rejectValue("email", "email.notvalid", "Wrong Email");
	    	return "login-view";
	    }
	    
	    // sprawdzanie hasla
	    if (new BCryptPasswordEncoder().matches(user.getPassword(), userFromDB.getPassword())) {
	    	
	    	// dodanie do sesji
	    	session.setAttribute("savedUser", userFromDB);
	    	
	    	// redirect na glowna strone po loginie
	    	return "redirect:http://localhost:8080/userService/mainPage";
	    } else {
	    	
	    	// powrot do forma logowania z bledem
	    	bindingResult.rejectValue("password", "password.notvalid", "Wrong Password");
	    	return "login-view";
	    }
	}
	
	/**
	 * Main site after login/register
	 * @param model
	 * @return
	 */
	@GetMapping("/mainPage")
	public String getMainPage(Model model) {
		List games = userService.getNewGames();
    	
    	model.addAttribute("games", games);
    	
    	return "afterLogin/first-view";
	}
	
	/**
	 * Wyswietlenie dokladych informacji o grze
	 * @param id - id gry do wyswietlenia
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/game/gameInfo/{id}", method = RequestMethod.GET )
	public String getGameInfo(@PathVariable("id") Long id, Model model) {
		// Pobranie informacji o grze
		Object game = userService.getInfoGame(id);
		
		// dodanie do view gry
		model.addAttribute("game", game);
		
		// Pobranie usera z sesji
        User user = (User)session.getAttribute("savedUser");
		
        // Dodanie usera do view
		model.addAttribute("user", user);
		
		// Pobranie konta usera
        Object acc = userService.getAccount(user.getId());
		
        // Dodanie konta usera do view
		model.addAttribute("acc", acc);
		
		// Pobranie czy gra jest kupione przez usera czy nie
		Object gameStatus = userService.checkBoughtStatus(user.getId(), id);

		// jezeli nie ustawiam isBought na false, jezeli tak to na true
		if (gameStatus != null)
			model.addAttribute("isBought", true);
		else 
			model.addAttribute("isBought", false);
		
		// laduje widok z gra
		return "afterLogin/game-view";
	}
	
	/**
	 * Strona z iloscia kwoty na koncie usera
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/money", method = RequestMethod.GET)
	public String getAddMoneyView(Model model) {
		User user = (User)session.getAttribute("savedUser");
		
		model.addAttribute("user", user);
		
		Object acc = userService.getAccount(user.getId());
		
		model.addAttribute("acc", acc);
		
		return "afterLogin/money-view";
	}
	
	/**
	 * Wylogowanie usera
	 * @return
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute("savedUser");
		return "redirect:http://localhost:8080/userService/";
	}
	
	/**
	 * Strona z widokiem gier kupionych przez usera
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/yourGames", method = RequestMethod.GET)
	public String yourGames(Model model) {
		User user = (User)session.getAttribute("savedUser");
		
		List games = userService.getBoughtGames(user.getId());
		
		model.addAttribute("games", games);
		
		return "afterLogin/your-games-view";
	}
	
	/**
	 * Wyswietlenie gier po kategorii
	 * @param category - kategoria do wyswietlenie
	 * @return
	 */
	@RequestMapping(path = "/game/category/{cat}", method = RequestMethod.GET)
	public String getGamesThroughCat(@PathVariable("cat") String category, Model model) {
		List games = userService.getGamesCat(category);
		
		model.addAttribute("games", games);
		
		model.addAttribute("title", category);
		
		return "afterLogin/cat-games-view";
	}
}
