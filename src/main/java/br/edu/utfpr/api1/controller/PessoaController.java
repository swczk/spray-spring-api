package br.edu.utfpr.api1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.model.Pessoa;
import br.edu.utfpr.api1.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoa", produces = "application/json")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @GetMapping("/1")
    public Pessoa getOne(){
        var p = new Pessoa(1, "eduardo", "1eduardofadel@gmail.com");
        return p;
    }

    @PostMapping
    public Pessoa create (@RequestBody Pessoa p) {
        return pessoaRepository.save(p);    
    
    }

}
