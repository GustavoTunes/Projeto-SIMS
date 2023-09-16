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
	public String cubatao(Model model) {
		return puxarNoticias("cubatao", model);
	}

	@GetMapping("/guaruja")
	public String guaruja(Model model) {
		return puxarNoticias("guaruja", model);
	}

	@GetMapping("/itanhaem")
	public String itanhaem(Model model) {
		return puxarNoticias("itanhaem", model);
	}

	@GetMapping("/mongagua")
	public String mongagua(Model model) {
		return puxarNoticias("mongagua", model);
	}

	@GetMapping("/peruibe")
	public String peruibe(Model model) {
		return puxarNoticias("peruibe", model);
	}

	@GetMapping("/praiagrande")
	public String praiagrande(Model model) {
		return puxarNoticias("praiagrande", model);
	}

	@GetMapping("/santos")
	public String santos(Model model) {
		return puxarNoticias("santos", model);
	}

	@GetMapping("/saovicente")
	public String saovicente(Model model) {
		return puxarNoticias("saovicente", model);
	}

	//
	
	private static final Map<String, String> siteCidade = new HashMap<>();
	private static final Map<String, String> seletorCidade = new HashMap<>();

	static {
		
		// associando e armazenando urls específicas para cada cidade
		
		siteCidade.put("bertioga", "https://www.bertioga.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("cubatao", "https://www.cubatao.sp.gov.br/?s=sa%C3%BAde");
		siteCidade.put("guaruja", "https://www.guaruja.sp.gov.br/categoria/saude/");
		siteCidade.put("itanhaem", "https://www2.itanhaem.sp.gov.br/tag/saude/");
		siteCidade.put("mongagua", "https://www.mongagua.sp.gov.br/noticias/saude");
		siteCidade.put("peruibe", "http://www.peruibe.sp.gov.br/secao/saude/");
		siteCidade.put("praiagrande", "https://www.praiagrande.sp.gov.br/pgnoticias/noticias/assunto_noticia.asp?idAssunto=14");
		siteCidade.put("santos", "https://www.santos.sp.gov.br/lista-de-noticias/151");
		siteCidade.put("saovicente", "https://www.saovicente.sp.gov.br/pesquisa?pesquisa=vacina%E7%E3o");

		// associando e armazenando seletores específicos para cada cidade
		
		seletorCidade.put("bertioga", ".news__wrapper");
		seletorCidade.put("cubatao", ".pp-post-wrap");
		seletorCidade.put("guaruja", ".mvp-blog-story-text");
		seletorCidade.put("itanhaem", ".row");
		seletorCidade.put("mongagua", ".list-item");
		seletorCidade.put("peruibe", "article");
		seletorCidade.put("praiagrande", ".borda_top_cinza");
		seletorCidade.put("santos", ".grid-item.col-xs-12.col-sm-6.col-md-4.col-lg-4");
		seletorCidade.put("saovicente", ".desc-noticia");
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
						
						Elements links = noticia.select("div a"); // Obter todos os links dentro do h4
						
						 for (Element link : links) {
						    	
						    	if (link != null) {
						    	
						        String urlNoticia = link.attr("href");

						        // Fazer uma solicitação HTTP para a URL da notícia
						        Document noticiaDocument = Jsoup.connect(urlNoticia).get();

				                // Verifica se encontrou um ponto e extrai a frase até esse ponto
						        Elements divs = noticiaDocument.select("div.post-content__content");

					                String texto = divs.first().text();
						
					                int primeiroPonto = texto.indexOf('.');

					                String fraseDesejada = (primeiroPonto >= 0) ? texto.substring(0, primeiroPonto + 1) : texto;

						String imagem = noticia.select("img").attr("src");
						String title = noticia.select(".news__title").text();
						String data = noticia.select(".news__details").text();
						String content = fraseDesejada;

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
						    	}
						 }
	                
					}
					
					break;
				
				case "cubatao":
					
					
					
					for (Element noticia : noticias) {
						
						// Seletor para o link dentro da notícia
						    Elements links = noticia.select(".elementor-widget-theme-post-title h1 a"); // Obter todos os links dentro do h4

						    // Iterar sobre os links dentro do h4
						    for (Element link : links) {
						    	
						    	if (link != null) {
						    	
						        String urlNoticia = link.attr("href");
						// Fazer uma solicitação HTTP para a URL da notícia
				        
						String imagem = noticia.select("img").attr("src");
						String title = noticia.select(".elementor-widget-theme-post-title h1").text();
						String data = noticia.select(".elementor-icon-list-text").text();
						String content = noticia.select(".elementor-widget-container").text();
						
						content = content.replace(title, "").replace(data, "");

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
										
					}
						    }
					}
					
					break;
					
				case "guaruja":
					
					for (Element noticia : noticias) {
					    // Seletor para o link dentro da notícia
					   /* Elements links = noticia.select("h2 a"); // Obter todos os links dentro do h4

					    // Iterar sobre os links dentro do h4
					    for (Element link : links) {
					    	
					    	if (link != null) {
					    	
					        String urlNoticia = link.attr("href");

					        // Fazer uma solicitação HTTP para a URL da notícia
					        Document noticiaDocument = Jsoup.connect(urlNoticia).get();*/
					        
						String imagem = noticia.select(".mvp-reg-img").attr("src");
						String title = noticia.select("h2").text();
						String data = noticia.select(".mvp-cd-date left relative span").text();
						String content = noticia.select("p").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
					    
					    	//}
					    	//}
					    	}
					
					/*for (Element noticia : noticias) {
						String imagem = noticia.select(".mvp-reg-img").attr("src");
						String title = noticia.select("h2").text();
						String data = noticia.select(".mvp-cd-date left relative span").text();
						String content = noticia.select("p").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
					    
					}*/
					
					break;
					
				case "itanhaem":
					
					for (Element noticia : noticias) {
					    // Seletor para o link dentro da notícia
					    Elements links = noticia.select("h4 a"); // Obter todos os links dentro do h4

					    // Iterar sobre os links dentro do h4
					    for (Element link : links) {
					    	
					    	if (link != null) {
					    	
					        String urlNoticia = link.attr("href");

					        // Fazer uma solicitação HTTP para a URL da notícia
					        Document noticiaDocument = Jsoup.connect(urlNoticia).get();

					        Element primeiroParagrafo = noticiaDocument.select("article p").first();
					        // Extrair informações da notícia
					        String imagem = noticiaDocument.select("figure img").attr("src");
					        String title = link.text(); // Texto dentro do h4
					        String data = link.parent().nextElementSibling().text();

					        Noticia topico = new Noticia(imagem, title, data, primeiroParagrafo.text());
					        noticiasList.add(topico);
					    }
					    }
					}
					
					break;
					
				case "mongagua":
					
					for (Element noticia : noticias) {
						
						Elements links = noticia.select("h3 a"); // Obter todos os links dentro do h4

					    // Iterar sobre os links dentro do h4
					    for (Element link : links) {
					    	
					    	if (link != null) {
					    	
					        String urlNoticia = "https://www.mongagua.sp.gov.br" + link.attr("href");

					        // Fazer uma solicitação HTTP para a URL da notícia
					        Document noticiaDocument = Jsoup.connect(urlNoticia).get();

						String imagem = noticiaDocument.select(".post-content img").attr("src");
						String title = noticia.select(".list-title").text();
						String data = noticiaDocument.select(".page-content time").text().substring(0, 10);
						String content = noticiaDocument.select(".post-content span").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
	                    
	                    System.out.println(content);
	                    //
					    	}
					    }
					}
					
					break;	

				case "peruibe":
					
					for (Element noticia : noticias) {

						Elements links = noticia.select("h2 a"); // Obter todos os links dentro do h4

					    // Iterar sobre os links dentro do h4
					    for (Element link : links) {
					    	
					    	if (link != null) {
					    	
					        String urlNoticia = link.attr("href");

					        // Fazer uma solicitação HTTP para a URL da notícia
					        Document noticiaDocument = Jsoup.connect(urlNoticia).get();

						
						String imagem = noticiaDocument.select(".zak-content img").attr("src");
						String title = noticia.select("h2").text();
						String data = noticiaDocument.select(".entry-date").text();
						String content = noticia.select("p").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
					    	}
					    }
					}
					
					break;
					
				case "praiagrande":
					
					for (Element noticia : noticias) {
						String imagem = noticia.select("img").attr("href");
						String title = noticia.select(".borda_top_cinza td").text();
						String data = noticia.select(".olho_chamada font").text();
						String content = noticia.select(".olho_chamada td").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
	                    
					}
					
					break;
					
				case "santos":
					
					for (Element noticia : noticias) {
						String imagem = noticia.select("img").attr("src");
						String title = noticia.select("h3").text();
						String data = noticia.select(".field-content").text();
						String content = noticia.select(".field field-name-body field-type-text-with-summary field-label-hidden div").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
	                    
					}
					
					break;

				case "saovicente":
					
					for (Element noticia : noticias) {
						String imagem = noticia.select("img").attr("src");
						String title = noticia.select("h3").text();
						String data = noticia.select(".data-pesquisa-materia span").text();
						String content = noticia.select(".field field-name-body field-type-text-with-summary field-label-hidden div").text();

						Noticia topico = new Noticia(imagem, title, data, content);
	                    noticiasList.add(topico); // Adicione cada notícia à lista
	                    
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