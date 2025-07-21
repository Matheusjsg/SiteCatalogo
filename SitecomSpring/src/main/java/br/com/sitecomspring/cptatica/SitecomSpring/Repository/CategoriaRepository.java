package br.com.sitecomspring.cptatica.SitecomSpring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sitecomspring.cptatica.SitecomSpring.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
