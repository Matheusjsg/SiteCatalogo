package br.com.sitecomspring.cptatica.SitecomSpring.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    @Column(name = "nome do produto")
    private String nome;
    @Column(name = "descricao do produto")
    private String descricao;
    @Column(name = "preco do produto")
    private double preco;

    private String imagemurl;
    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    // Construtor
    public Produto(String nome, String descricao, double preco, String imagemurl, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.imagemurl = imagemurl;
        
    }
    public Produto() {
    }

    // Getters e Setters
    public long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagemurl() {
        return imagemurl;
    }

    public void setImagemurl(String imagemurl) {
        this.imagemurl = imagemurl;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
