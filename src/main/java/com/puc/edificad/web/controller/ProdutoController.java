package com.puc.edificad.web.controller;

import com.puc.edificad.web.support.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @GetMapping
    public String exibirFormulario(Model model) {

        model.addAttribute("produto", new Produto());

        // Retorna a página do formulário
        return "state/produto";
    }

    @PostMapping
    String salvar(Produto produto){
        if(produto == null)
            System.out.println("nulo");

        return "state/produto";
    }

    private List<Produto> obterListaDeProdutos() {
        // Implemente a lógica para obter a lista de produtos (pode ser do banco de dados)
        // Este é apenas um exemplo, você pode buscar a lista do seu banco de dados
        List<Produto> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(new Produto(1L, "Produto 1", null));
        listaDeProdutos.add(new Produto(2L, "Produto 2", null));
        listaDeProdutos.add(new Produto(3L, "Produto 3", null));
        return listaDeProdutos;
    }
}
