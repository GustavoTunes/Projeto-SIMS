package com.projeto.sims.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

	@GetMapping("/teste")
	public String teste() {
		return "foi?";
	}
	
}
