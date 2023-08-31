package com.projeto.sims.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;

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
	
	private String getNewsForCity(String cidade, Model model) {
	    String url = "https://www.bertioga.sp.gov.br/?s=sa%C3%BAde"; // Substitua pela URL do site de notícias

	    try {
			/*
			 * Document document = Jsoup.connect(url).get();
			 * 
			 * Elements newsRows = document.select(".news__wrapper"); // Seletor para as
			 * linhas da tabela de notícias
			 * 
			 * StringBuilder newsForCity = new StringBuilder();
			 * 
			 * for (Element article : newsRows) { String title =
			 * article.select(".news__title").text(); // Seletor para o título do artigo
			 * String content = article.select(".news__excerpt").text(); // Seletor para o
			 * conteúdo do artigo
			 * 
			 * System.out.println(title);
			 * 
			 * if (title.contains(cidade) || content.contains(cidade)) { if
			 * (content.contains("saúde")) {
			 * newsForCity.append("<h2>").append(title).append("</h2>");
			 * newsForCity.append("<p>").append(content).append("</p>"); } } }
			 * 
			 * model.addAttribute("city", cidade); model.addAttribute("news",
			 * newsForCity.toString());
			 */

	    	StringBuilder newsForCity = new StringBuilder();
	    	
	    	Document document = Jsoup.connect(url).get();
	    	Elements noticias = document.select(".news__wrapper");
	    	
	    	for (Element noticia : noticias) {
	    		String title = noticia.select(".news__title").text(); // Seletor para o título do artigo
	    		String content = noticia.select(".news__excerpt").text(); // Seletor para o
	    		
	    		System.out.println(title);
	    		System.out.println(content);
	    		
	    	
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

    
