package com.example.pays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountRestController {

	@Autowired
	AccountRepository accountRepo;
	
	/**
	 * Ustawienie balance na koncie
	 * @param id -  id konta usera
	 * @param money - balance
	 * @return
	 */
	@RequestMapping(path = "/set/{id}/{money:.+}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Account setAccount(@PathVariable("id") Long id, @PathVariable("money") Double money) {
		Account acc = new Account();
		
		acc.setIdaccount(id);
		
		acc.setBalance(money);
		
		accountRepo.save(acc);
		
		return acc;
	}
	
	/**
	 * Dodanie pieniedzy do balance
	 * @param money - kwota do dodania
	 * @param id - id konta usera
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(path = "/add/{money}/{id}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Account add(@PathVariable("money") Double money, @PathVariable("id") Long id) {
		Account acc = accountRepo.findOne(id);
		
		Double currentBalance = acc.getBalance();
		
		acc.setBalance(money + currentBalance);
		
		accountRepo.save(acc);
		
		return acc;
	}
	
	/** 
	 * Pobierania balance usera
	 * @param id - id konta usera
	 * @return
	 */
	@RequestMapping(path = "/getMoney/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Account getMoney(@PathVariable("id") Long id) {
		Account acc = accountRepo.findOne(id);
		
		return acc;
	}
}
