package br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sitecomspring.cptatica.SitecomSpring.infrastructure.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
