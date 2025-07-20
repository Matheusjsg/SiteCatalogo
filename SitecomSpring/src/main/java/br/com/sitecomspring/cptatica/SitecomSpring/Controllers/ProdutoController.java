package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import br.com.sitecomspring.cptatica.SitecomSpring.Model.Produto;
import br.com.sitecomspring.cptatica.SitecomSpring.Repository.ProdutoRepository;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    private 

    @GetMapping("/categoria/{categoria}")
    public String listarPorCategoria(@PathVariable String categoria, Model model) {
        List<Produto> produtos = produtoRepository.findByCategoriaIgnoreCase(categoria);
        model.addAttribute("produtos", produtos);
        model.addAttribute("categoria", categoria);
        return "produtos"; // produtos.html
    }
}
