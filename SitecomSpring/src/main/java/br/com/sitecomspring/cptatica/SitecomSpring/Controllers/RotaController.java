package br.com.sitecomspring.cptatica.SitecomSpring.Controllers;
   
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RotaController {

  

    @GetMapping("/home") // ou "/index"
    public String paginaInicial() {
        return "index"; // busca por index.html dentro de templates
        }
    

    @GetMapping("/municoes")
    public String paginaMunicoes() {
        return "municoes"; // busca por municoes.html dentro de templates
    }

    @GetMapping("/equipamentos")
    public String paginaEquipamentos() {
        return "equipamentos"; // busca por equipamentos.html dentro de templates
    }

    @GetMapping("/tiroesportivo")
    public String paginaTiroEsportivo() {
        return "tiroesportivo"; // busca por tiroesportivo.html dentro de templates
    }


    @GetMapping("/acessorios")
    public String paginaAcessorios() {
        return "acessorios"; // busca por acessorios.html dentro de templates
    }


    @GetMapping("/carabinas")
    public String paginaCarabinas() {
        return "carabinas"; // busca por carabinas.html dentro de templates
    }

}