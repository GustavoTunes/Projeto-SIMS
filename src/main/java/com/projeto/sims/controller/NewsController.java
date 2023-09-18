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
		siteCidade.put("praiagrande",
				"https://www.praiagrande.sp.gov.br/pgnoticias/noticias/assunto_noticia.asp?idAssunto=14");
		siteCidade.put("santos", "https://www.santos.sp.gov.br/lista-de-noticias/151");
		siteCidade.put("saovicente", "https://www.saovicente.sp.gov.br/pesquisa?pesquisa=saude");

		// associando e armazenando seletores específicos para cada cidade

		seletorCidade.put("bertioga", ".news__wrapper");
		seletorCidade.put("cubatao", ".pp-post-wrap");
		seletorCidade.put("guaruja", ".archive.category.category-saude.category-215.real-accessability-body");
		seletorCidade.put("itanhaem", ".row");
		seletorCidade.put("mongagua", ".list-item");
		seletorCidade.put("peruibe", "article");
		seletorCidade.put("praiagrande", "div#divAssuntoNoticia");
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

			int contadorLinks = 0;

			switch (cidade) {

			case "bertioga":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					Elements links = noticia.select("div a"); // Obter todos os links dentro do h4

					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = link.attr("href");

							try {

								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								// Verifica se encontrou um ponto e extrai a frase até esse ponto
								Elements divs = noticiaDocument.select("div.post-content__content");

								String texto = divs.first().text();

								int primeiroPonto = texto.indexOf('.');

								String fraseDesejada = (primeiroPonto >= 0) ? texto.substring(0, primeiroPonto + 1)
										: texto;

								String imagem = noticia.select("img").attr("src");
								String title = noticia.select(".news__title").text();
								String data = "Publicado em " + noticia.select(".news__details").text();
								String content = fraseDesejada;

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "cubatao":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					// Seletor para o link dentro da notícia
					Elements links = noticia.select(".elementor-widget-theme-post-title h1 a"); // Obter todos os links
																								// dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String imagem = noticia.select("img").attr("src");
							String title = noticia.select(".elementor-widget-theme-post-title h1").text();
							String data = noticia.select(".elementor-icon-list-text").text();
							String content = noticia.select(".elementor-widget-container").text();

							content = content.replace(title, "").replace(data, "");

							Noticia topico = new Noticia(imagem, title, data, content);
							noticiasList.add(topico); // Adicione cada notícia à lista
							contadorLinks++;
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "guaruja":

				contadorLinks = 0;

				for (Element noticia : noticias) {
					// Seletor para o link dentro da notícia
					Elements links = noticia.select(
							".mvp-widget-feat2-left a, .mvp-widget-feat2-right a, .mvp-blog-story-wrap.left.relative.infinite-post a"); 
					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = link.attr("href");

							try {

								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								String imagem = noticiaDocument.select("#mvp-post-feat-img img").attr("src");
								String title = noticiaDocument.select("h1").text();
								String data = noticiaDocument.select("time").text();
								String content = noticiaDocument.select("em").text();

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista

								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "itanhaem":

				contadorLinks = 0;

				for (Element noticia : noticias) {
					// Seletor para o link dentro da notícia
					Elements links = noticia.select("h4 a"); // Obter todos os links dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = link.attr("href");

							try {

								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								Element primeiroParagrafo = noticiaDocument.select("article p").first();
								// Extrair informações da notícia
								String imagem = noticiaDocument.select("figure img").attr("src");
								String title = link.text(); // Texto dentro do h4
								String data = link.parent().nextElementSibling().text();

								Noticia topico = new Noticia(imagem, title, data, primeiroParagrafo.text());
								noticiasList.add(topico);
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "mongagua":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					Elements links = noticia.select("h3 a"); // Obter todos os links dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = "https://www.mongagua.sp.gov.br" + link.attr("href");

							try {
								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								String imagem = noticiaDocument.select(".post-content img").attr("src");
								String title = noticia.select(".list-title").text();
								String data = noticiaDocument.select(".page-content time").text().substring(0, 10);
								String content = noticiaDocument.select(".post-content span").text();

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista

								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "peruibe":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					Elements links = noticia.select("h2 a"); // Obter todos os links dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = link.attr("href");

							try {

								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								String imagem = noticiaDocument.select(".zak-content img").attr("src");
								String title = noticia.select("h2").text();
								String data = noticiaDocument.select(".entry-date").text();
								String content = noticia.select("p").text();

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "praiagrande":

				contadorLinks = 0;

				// Iterar sobre as notícias
				for (Element noticia : noticias) {
					Elements links = noticia.select("td a"); // Obter todos os links dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {
						// Verifique se o link não é nulo e se já processou 9 links
						if (link != null && contadorLinks < 9) {
							String urlNoticia = "https://www.praiagrande.sp.gov.br/pgnoticias/noticias/"
									+ link.attr("href");

							try {
								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								// Extrair outras informações da notícia
								String imagem = noticiaDocument.select("#divCadaNoticia img").attr("src");
								String title = noticiaDocument.select(".olho_foto").text();
								String data = "Publicado em: "
										+ noticiaDocument.select("td.txt_pg").first().text().substring(0, 9);
								String content = noticiaDocument.select(".olho_chamada").text();

								// Criar o objeto Noticia e adicionar à lista
								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico);

								// Incrementar o contador de links processados
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;
			// ...

			case "santos":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					Elements links = noticia.select(".grid-item.col-xs-12.col-sm-6.col-md-4.col-lg-4 a"); // Obter todos
																											// os links
																											// dentro do
																											// h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = link.attr("href");

							try {
								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								String imagem = noticia.select("img").attr("src");
								String title = noticia.select("h3").text();
								String data = "Publicado em: " + noticia.select(".field-content").text();
								String content = noticiaDocument.select(".field-item.even p").first().text();

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
				}
				break;

			case "saovicente":

				contadorLinks = 0;

				for (Element noticia : noticias) {

					Elements links = noticia.select("hgroup a"); // Obter todos os links dentro do h4

					// Iterar sobre os links dentro do h4
					for (Element link : links) {

						if (link != null && contadorLinks < 9) {

							String urlNoticia = "https://www.saovicente.sp.gov.br/" + link.attr("href");

							try {

								// Fazer uma solicitação HTTP para a URL da notícia
								Document noticiaDocument = Jsoup.connect(urlNoticia).get();

								String imagem = noticiaDocument.select("figure.resultado-noticia").attr("style");

								String title = noticia.select("h3").text();
								String data = noticia.select(".data-pesquisa-materia span").text();
								String content = noticiaDocument.select(".desc-noticia h3").text();

								Noticia topico = new Noticia(imagem, title, data, content);
								noticiasList.add(topico); // Adicione cada notícia à lista
								contadorLinks++;
							} catch (IOException e) {
								// Imprime informações sobre a exceção para depuração
								e.printStackTrace();
								System.err.println("Erro ao processar a URL da notícia: " + urlNoticia);
							}
						} else {
							// Se já processou 9 links, sai do loop
							break;
						}
					}
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