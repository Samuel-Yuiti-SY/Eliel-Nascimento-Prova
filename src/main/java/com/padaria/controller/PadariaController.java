package com.padaria.controller;

import com.padaria.model.Padaria;
import com.padaria.service.PadariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/padarias")
public class PadariaController {

    private final PadariaService service;

    public PadariaController(PadariaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Padaria>> listar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.listar());
    }

    @PostMapping
    public ResponseEntity<Padaria> criar(@RequestBody Padaria padaria) {
        return ResponseEntity.ok(service.salvar(padaria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Padaria> buscarPorId(@PathVariable Long id) {
        Padaria padaria = service.buscarPorId(id);

        if (padaria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(padaria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!service.deletar(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Padaria> atualizar(@PathVariable Long id, @RequestBody Padaria dados) {
        Padaria padaria = service.atualizar(id, dados);

        if (padaria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(padaria);
    }
}
