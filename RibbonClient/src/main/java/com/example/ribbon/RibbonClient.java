package com.example.ribbon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class RibbonClient {
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/user_addresses/")
	public List getAddresses() {
		List lista = this.restTemplate.getForObject("http://user-service/user/", List.class);
		return lista;
	}
	
	@RequestMapping("/games_addresses/")
	public List getGamesAddresses() {
		List lista = this.restTemplate.getForObject("http://games-service/game/", List.class);
		return lista;
	}
	
	@RequestMapping("/account_addresses/")
	public List getPayAddresses() {
		List lista = this.restTemplate.getForObject("http://payment-service/account/", List.class);
		return lista;
	} 
	
	@RequestMapping("/bought_addresses/")
	public List getBoughtAddresses() {
		List lista = this.restTemplate.getForObject("http://bought-service/bought/", List.class);
		return lista;
	} 
	public static void main(String[] args) {
		SpringApplication.run(RibbonClient.class, args);
	}
}