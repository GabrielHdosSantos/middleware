package com.br.integracao.middleware.resources;

import com.br.integracao.middleware.dto.FuncionarioDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/funcionario")
public class MiddlewareResources {


    @PostMapping("adicionar")
    public ResponseEntity<FuncionarioDTO> adicionarFuncionarioAPI(@RequestBody FuncionarioDTO funcionarioDTO) {
        FuncionarioDTO newFuncionario = new FuncionarioDTO(funcionarioDTO.nome, funcionarioDTO.cpf, funcionarioDTO.dataContratacao);

        ResponseEntity<FuncionarioDTO> objeto = new RestTemplate().exchange("http://localhost:9091/funcionarios",
                HttpMethod.POST, new HttpEntity<>(newFuncionario), FuncionarioDTO.class);

        return objeto;
    }

    @GetMapping("listar")
    public ResponseEntity<FuncionarioDTO[]> listartodos() {
        ResponseEntity<FuncionarioDTO[]> lista = new RestTemplate().getForEntity("http://localhost:9091/funcionarios", FuncionarioDTO[].class);
        FuncionarioDTO[] funcionarios = lista.getBody();

        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @PostMapping("/listaPeloNome")
    public ResponseEntity<FuncionarioDTO[]> listaNome(@RequestBody FuncionarioDTO funcionarioDTO) {
        FuncionarioDTO newFuncionario = new FuncionarioDTO(funcionarioDTO.nome);

        ResponseEntity<FuncionarioDTO[]> listagem = new RestTemplate().exchange("http://localhost:9090/filtro",
                HttpMethod.POST, new HttpEntity<>(newFuncionario), FuncionarioDTO[].class);

        FuncionarioDTO[] funcionarios = listagem.getBody();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }


}
