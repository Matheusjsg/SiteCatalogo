package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.Produto;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository.CategoriaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository.ProdutoRepository;

import org.springframework.ui.Model;





@Controller
@RequestMapping("/")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    
    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }
    

    @GetMapping
    public String paginainicial (Model model){
        return "index";
    }
    

@GetMapping("categoria/{nome}")
public String listarPorCategoria(@PathVariable String nome, Model model) {
    List<Produto> produtos = produtoRepository.findByCategoria_NomeIgnoreCase(nome);
    model.addAttribute("produtos", produtos);
    model.addAttribute("categoria", nome);
    model.addAttribute("categorias", categoriaRepository.findAll());
    model.addAttribute("categoriaSelecionada", nome);

    return "produtos";
    }

@GetMapping("categoria/{categoria}/{slug}")
        
  public String ExibirProdutosCategoria(@PathVariable String categoria,
                                        @PathVariable String slug,
                                        Model model) {

    Produto produto = produtoRepository.findBySlugAndCategoria_NomeIgnoreCase(slug, categoria);
    if (produto == null) {
        throw new RuntimeException("Produto não encontrado");
    }

    model.addAttribute("produto", produto);
    return "produto";

}


    }


