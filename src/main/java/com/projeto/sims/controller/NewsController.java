package com.projeto.sims.controller;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@Controller
public class NewsController {
	
	@GetMapping("/bertioga")
	public String bertioga(Model model) {
		return getNewsForCity("Bertioga", model);
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

	
	private static final Map<String, String> siteCidade = new HashMap<>();

	static {
		
		siteCidade.put("Bertioga", "https://www.bertioga.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("Cubatão", "https://www.cubatao.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("Guaruja", ""); //link quebra pq n tem uri específica
		siteCidade.put("Itanhaem", ""); //link quebra pq n tem uri específica
		siteCidade.put("Mongagua", ""); //link quebra pq n tem uri específica
		siteCidade.put("Peruibe", "http://www.peruibe.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("PraiaGrande", ""); //Tem que ser pesquisas mais específicas
		siteCidade.put("Santos", "https://www.santos.sp.gov.br/lista-de-noticias/151");
		siteCidade.put("SaoVicente", "https://www.saovicente.sp.gov.br/pesquisa?pesquisa=vacina%E7%E3o"); //Tem que ser pesquisas mais específicas
		
	}
	
	private String getNewsForCity(String cidade, String url, Model model) {

		Map<String, String> seletorCidade = new HashMap<>();
	    seletorCidade.put("bertioga", ".news__wrapper"); // Substitua pelo seletor correto para Bertioga
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
	    seletorCidade.put("cubatao", ".noticia"); 
		
	    String seletor = seletorCidade.get(cidade.toLowerCase());

	    if (seletor != null) {
	    	
	    try {
	    	StringBuilder newsForCity = new StringBuilder();
	    	
	    	Document document = Jsoup.connect(url).get();
	    	Elements noticias = document.select(".news__wrapper");
	    	
	    	for (Element noticia : noticias) {
	    		String title = noticia.select(".news__title").text(); // Seletor para o título do artigo
	    		String content = noticia.select(".news__excerpt").text(); // Seletor para o
	    		
				 newsForCity.append("<h2>").append(title).append("</h2>");
				 newsForCity.append("<p>").append(content).append("</p>"); 
				 
	    	}
	    	
	    model.addAttribute("city", cidade); model.addAttribute("news",
				 newsForCity.toString());

	    	
	        return "bertioga"; // Certifique-se de ter um template Thymeleaf adequado para renderizar as notícias.
	    
	    }
	    
	    catch (IOException e) {
	        e.printStackTrace();
	        model.addAttribute("city", cidade);
	        model.addAttribute("news", "Erro ao buscar notícias.");
	        return "bertioga";
	    }
	}
}

    
