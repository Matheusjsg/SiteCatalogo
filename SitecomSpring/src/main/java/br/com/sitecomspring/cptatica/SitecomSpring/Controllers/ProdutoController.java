package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Produto;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Categoria;
import br.com.sitecomspring.cptatica.SitecomSpring.Repository.CategoriaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.Repository.ProdutoRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/produtos")
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
    public String listarProdutos(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos";
    }

   
    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "novo";
    }
    
   @PostMapping("/salvar")
   public String salvarProduto(@RequestParam String nome, 
                               @RequestParam String descricao, 
                               @RequestParam double preco, 
                               @RequestParam String imagemurl, 
                               @RequestParam Long categoriaId) {

       Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
       if (categoria != null) {
           Produto produto = new Produto(nome, descricao, preco, imagemurl, categoria);
           produtoRepository.save(produto);
       }
       return "redirect:/produtos";
   }

   @GetMapping("editar/{id}")
   public String editarProduto(@PathVariable Long id, Model model) {
       Produto produto = produtoRepository.findById(id).orElse(null);
       if (produto != null) {
           model.addAttribute("produto", produto);
           model.addAttribute("categorias", categoriaRepository.findAll());
           return "editar-produto";
       }
       return "redirect:/produtos";

   }
      @GetMapping("excluir/{id}")
   public String excluirProduto(@PathVariable Long id) {
       Produto produto = produtoRepository.findById(id).orElse(null);
       if (produto != null) {
           produtoRepository.delete(produto);
       }
       return "redirect:/produtos";
    }

@GetMapping("/categoria/{nome}")
public String listarPorCategoria(@PathVariable String nome, Model model) {
    List<Produto> produtos = produtoRepository.findByCategoria_NomeIgnoreCase(nome);
    model.addAttribute("produtos", produtos);
    model.addAttribute("categoria", nome);
    model.addAttribute("categorias", categoriaRepository.findAll());
    model.addAttribute("categoriaSelecionada", nome);

    return "produtos";
}


   }

