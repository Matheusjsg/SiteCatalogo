package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.Categoria;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.ImagemProduto;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.Produto;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository.CategoriaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository.ProdutoRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
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

 

  @GetMapping()
  public String allproducts(Model model) {
    List<Produto> produtos = produtoRepository.findAll();
    model.addAttribute("produtos", produtos);

    return "allproducts";
  }
  


    @GetMapping("/novo")
  
    public String novoProduto(Model model) {
        
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
       
        return "novo";
    }


   
   @PostMapping("/salvar")
public String salvarProduto(@RequestParam String nome, 
                            @RequestParam String descricaolink, 
                            @RequestParam String descricaoCompleta, 
                            @RequestParam double preco, 
                            @RequestParam("imagem") MultipartFile[] imagem,
                            @RequestParam Long categoriaId) {

    Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

    if (categoria != null && imagem != null && imagem.length > 0) {
        Produto produto = new Produto(nome, descricaolink, descricaoCompleta, preco, categoria);
        produto.setSlug(gerarSlug(nome));

        try {
            Path pastaUploads = Paths.get("uploads");
            if (!Files.exists(pastaUploads)) {
                Files.createDirectories(pastaUploads);
            }

            for (MultipartFile img : imagem) {
                if (!img.isEmpty()) {
                    String extensao = "";
                    String nomeOriginal = img.getOriginalFilename();
                    if (nomeOriginal != null && nomeOriginal.contains(".")) {
                        extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                    }

                    String nomeArquivo = gerarSlug(nome) + "-" + UUID.randomUUID() + extensao.toLowerCase();
                    Path caminhoArquivo = pastaUploads.resolve(nomeArquivo);
                    Files.copy(img.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

                    ImagemProduto imgProduto = new ImagemProduto(nomeArquivo, produto);
                    produto.getImagens().add(imgProduto);
                }
            }

            produtoRepository.save(produto);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar as imagens", e);
        }
    }

    return "redirect:/cptadmin/novo";
}
@GetMapping("/editar/{id}")
public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
   
   
    Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    List<Categoria> categorias = categoriaRepository.findAll();

    model.addAttribute("produto", produto);
    model.addAttribute("categorias", categorias);

    return "editar"; // nome do HTML que vai exibir o form de edição
}


@PostMapping("/editar/{id}")

public String editarProduto(@PathVariable Long id,
                            @RequestParam String nome, 
                            @RequestParam String descricaolink, 
                            @RequestParam String descricaoCompleta, 
                            @RequestParam double preco, 
                            @RequestParam(value = "imagem", required = false) MultipartFile[] imagem,
                            @RequestParam Long categoriaId) {

    Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    Categoria categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

    produto.setNome(nome);
    produto.setDescricaolink(descricaolink);
    produto.setDescricaoCompleta(descricaoCompleta);
    produto.setPreco(preco);
    produto.setCategoria(categoria);
    produto.setSlug(gerarSlug(nome));

    if (imagem != null && imagem.length > 0) {
        try {
            Path pastaUploads = Paths.get("uploads");
            if (!Files.exists(pastaUploads)) {
                Files.createDirectories(pastaUploads);
            }

            for (MultipartFile img : imagem) {
                if (!img.isEmpty()) {
                    String extensao = "";
                    String nomeOriginal = img.getOriginalFilename();
                    if (nomeOriginal != null && nomeOriginal.contains(".")) {
                        extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                    }

                    String nomeArquivo = gerarSlug(nome) + "-" + UUID.randomUUID() + extensao.toLowerCase();
                    Path caminhoArquivo = pastaUploads.resolve(nomeArquivo);
                    Files.copy(img.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

                    ImagemProduto imgProduto = new ImagemProduto(nomeArquivo, produto);
                    produto.getImagens().add(imgProduto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar as imagens", e);
        }
    }

    produtoRepository.save(produto);

    return "redirect:/cptadmin"; // redireciona para a listagem de produtos do admin
}

   public String gerarSlug(String nome) {return nome.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");}



   

      @GetMapping("/excluir/{id}")
   public String excluirProduto(@PathVariable Long id) {
       Produto produto = produtoRepository.findById(id).orElse(null);
       if (produto != null) {
           produtoRepository.delete(produto);
       }
       return "redirect:/cptadmin";
    }

    
    
}
