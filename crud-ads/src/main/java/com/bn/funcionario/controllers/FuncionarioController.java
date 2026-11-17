package com.bn.funcionario.controllers;

import com.bn.funcionario.models.FuncionarioModel;
import com.bn.funcionario.services.FuncionarioServices;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    private final FuncionarioServices funcionarioServices;

    public FuncionarioController(FuncionarioServices funcionarioServices) {
        this.funcionarioServices = funcionarioServices;
    }

    @GetMapping
    public List<FuncionarioModel> findAll() {
        return funcionarioServices.findAll();
    }

    @GetMapping("/{id}")
    public FuncionarioModel buscarPorId(@PathVariable Long id) {
        return funcionarioServices.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<FuncionarioModel> criarFuncionario(@RequestBody FuncionarioModel funcionario) {
        FuncionarioModel criado = funcionarioServices.criarPessoa(funcionario);
        return ResponseEntity.created(URI.create("/api/funcionarios/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public FuncionarioModel atualizar(@PathVariable Long id, @RequestBody FuncionarioModel funcionario) {
        return funcionarioServices.atualizar(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioServices.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
