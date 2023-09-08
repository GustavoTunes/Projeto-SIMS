package com.projeto.sims;

public class Noticia {
    private String imagem;
    private String title;
    private String data;
    private String content;

    // Construtor
    public Noticia(String imagem, String title, String data, String content) {
        this.imagem = imagem;
        this.title = title;
        this.data = data;
        this.content = content;
    }

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
}
