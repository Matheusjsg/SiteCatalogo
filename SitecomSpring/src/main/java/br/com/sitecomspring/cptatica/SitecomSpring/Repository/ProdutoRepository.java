package br.com.sitecomspring.cptatica.SitecomSpring.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    

    List<Produto> findByCategoria_NomeIgnoreCase(String name);
}
