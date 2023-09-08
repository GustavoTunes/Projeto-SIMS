package com.projeto.sims.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;

import com.projeto.sims.Noticia;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NewsController {

	@GetMapping("/bertioga")
	public String bertioga(Model model) {
		return puxarNoticias("bertioga", model);
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

	//
	
	private static final Map<String, String> siteCidade = new HashMap<>();
	private static final Map<String, String> seletorCidade = new HashMap<>();

	static {
		
		// associando e armazenando urls específicas para cada cidade
		
		siteCidade.put("bertioga", "https://www.bertioga.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("cubatão", "https://www.cubatao.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("guaruja", "https://www.guaruja.sp.gov.br/categoria/saude/");
		siteCidade.put("itanhaem", "https://www2.itanhaem.sp.gov.br/tag/saude/");
		siteCidade.put("mongagua", "https://www.mongagua.sp.gov.br/noticias/saude");
		siteCidade.put("peruibe", "http://www.peruibe.sp.gov.br/secao/saude/");
		siteCidade.put("praiagrande", "https://www.praiagrande.sp.gov.br/pgnoticias/noticias/assunto_noticia.asp?idAssunto=14");
		siteCidade.put("santos", "https://www.santos.sp.gov.br/lista-de-noticias/151");
		siteCidade.put("saovicente", "https://www.saovicente.sp.gov.br/pesquisa?pesquisa=vacina%E7%E3o");

		// associando e armazenando seletores específicos para cada cidade
		
		seletorCidade.put("bertioga", ".news__wrapper");
		seletorCidade.put("cubatão", ".elementor-widget-wrap.elementor-element-populated");
		seletorCidade.put("guaruja", ".mvp-main-blog-out.left.relative");
		seletorCidade.put("itanhaem", ".col-lg-10.col-md-10.col-sm-12");
		seletorCidade.put("mongagua", "item");
		seletorCidade.put("peruibe", "article");
		seletorCidade.put("praiagrande", "tr");
		seletorCidade.put("santos", "grid-item.col-xs-12.col-sm-6.col-md-4.col-lg-4");
		seletorCidade.put("saovicente", ".noticia");
	}

	public String puxarNoticias(String cidade, Model model) {

		String url = siteCidade.get(cidade);
		String seletor = seletorCidade.get(cidade);

			try {
				Document document = Jsoup.connect(url).get();
				Elements noticias = document.select(seletor);

		        List<Noticia> noticiasList = new ArrayList<>(); // Crie uma lista de Noticias
				
				switch (cidade) {
				
				case "bertioga":
				
					for (Element noticia : noticias) {
						String imagem = noticia.select("img").attr("src");
						String title = noticia.select(".news__title").text();
						String data = noticia.select(".news__details").text();
						String content = noticia.select(".news__excerpt").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
	                
						
						/*System.out.println(title);
						System.out.println(content);
						
						newsForCity.append("<img>").append(imagem).append("/<img>");
						newsForCity.append("<h2>").append(title).append("</h2>");
						newsForCity.append("<div>").append(data).append("</div>");
						newsForCity.append("<p>").append(content).append("</p>");
					*/
					}
					
					break;
				
				default:
				
					model.addAttribute("cidade", cidade);
	                model.addAttribute("noticias", "Cidade não encontrada.");
	                
					return "error";
				}

				model.addAttribute("cidade", cidade);
		        model.addAttribute("noticiasList", noticiasList); // Adicione a lista de notícias ao modelo

		        // Retorna a página correspondente a cidade
		        
		        return cidade;

			} catch (IOException e) {
				
				e.printStackTrace();
				
				model.addAttribute("city", cidade);
				model.addAttribute("news", "Erro ao buscar notícias.");
				
				return "error";
			}
	}
}