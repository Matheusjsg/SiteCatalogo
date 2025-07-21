package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.sitecomspring.cptatica.SitecomSpring.Repository.CategoriaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Categoria;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Produto;
import br.com.sitecomspring.cptatica.SitecomSpring.Repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cptadmin")
public class AdmController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

   
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
       return "redirect:/cptadmin/novo";
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
       return "redirect:/cptadmin";
    }
    
}
