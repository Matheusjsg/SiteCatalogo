package br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity
@Table(name = "ImagemProduto")

public class ImagemProduto {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;

private String nomeArquivo;

@ManyToOne
@JoinColumn(name = "produto_id")
    private Produto produto;


public ImagemProduto() {}



public ImagemProduto(String nomeArquivo, Produto produto) {
        this.nomeArquivo = nomeArquivo;
        this.produto = produto;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getNomeArquivo() { 
        return nomeArquivo; 
    }
    public void setNomeArquivo(String nomeArquivo) {
         this.nomeArquivo = nomeArquivo; 
        }

    public Produto getProduto() { 
        return produto; 
    }
    public void setProduto(Produto produto) {
         this.produto = produto; 
        }
}