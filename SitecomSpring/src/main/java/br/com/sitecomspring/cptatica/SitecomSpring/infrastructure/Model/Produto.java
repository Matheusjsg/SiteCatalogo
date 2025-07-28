package br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "produtos")
public class Produto {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    @Column(name = "nome", unique = true)
    private String nome;

    @Column(name= "descricaolink")
    private String descricaolink;

    @Column(name = "descricaoCompleta", columnDefinition = "TEXT")
    private String descricaoCompleta;

    @Column(name = "preco")
    private double preco;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemProduto> imagens = new ArrayList<>();

    private String slug;
    
    @ManyToOne
    @JoinColumn(name = "categoria")
        private Categoria categoria;

    // Construtor
    public Produto(String nome, String descricaolink, String descricaoCompleta, double preco, Categoria categoria) {
        this.nome = nome;
        this.descricaolink = descricaolink;
        this.descricaoCompleta = descricaoCompleta;
        this.preco = preco;
        this.categoria = categoria;
        
    }

    public Produto() {}

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

      public String getDescricaolink() {
        return descricaolink;
    }

    public void setDescricaolink(String descricaolink) {
        this.descricaoCompleta = descricaolink;
    }

    public String getDescricao() {
        return descricaoCompleta;
    }

    public void setDescricao(String descricao) {
        this.descricaoCompleta = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

  

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<ImagemProduto> getImagens() { 
        return imagens; 
}
    public void setImagens(List<ImagemProduto> imagens) { 
        this.imagens = imagens; 
    }


}
