package br.edu.utfpr.api1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.model.Pessoa;

@RestController
@RequestMapping(value = "/pessoa", produces = "application/json")
public class PessoaController {
    
    @GetMapping("/1")
    public Pessoa getOne(){
        var p = new Pessoa(1, "eduardo", "1eduardofadel@gmail.com");
        return p;
    }

}
