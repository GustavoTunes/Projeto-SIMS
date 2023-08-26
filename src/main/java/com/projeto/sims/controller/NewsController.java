package com.projeto.sims.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewsController {
	
	@GetMapping("/bertioga")
	public String bertioga() {
		return "bertioga";
	}
	
	@GetMapping("/cubatao")
	public String cubatao() {
		return "cubatao";
	}
	
	@GetMapping("/guaruja")
	public String guaruja() {
		return "guaruja";
	}
	
	@GetMapping("/itanhaem")
	public String itanhaem() {
		return "itanhaem";
	}
	
	@GetMapping("/mongagua")
	public String mongagua() {
		return "mongagua";
	}
	
	@GetMapping("/peruibe")
	public String peruibe() {
		return "peruibe";
	}
	
	@GetMapping("/praiagrande")
	public String praiagrande() {
		return "praiagrande";
	}
	
	@GetMapping("/santos")
	public String santos() {
		return "santos";
	}
	
	@GetMapping("/saovicente")
	public String saovicente() {
		return "saovicente";
	}
}
