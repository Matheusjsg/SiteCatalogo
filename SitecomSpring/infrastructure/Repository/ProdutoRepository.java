package br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    

    List<Produto> findByCategoria_NomeIgnoreCase(String name);
    
    Produto findBySlugAndCategoria_NomeIgnoreCase(String slug, String categoria);
}
