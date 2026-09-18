package com.padaria.service;

import com.padaria.model.Padaria;
import com.padaria.repository.PadariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PadariaService {

    private final PadariaRepository repository;

    public PadariaService(PadariaRepository repository) {
        this.repository = repository;
    }

    public List<Padaria> listar() {
        return repository.findAll();
    }

    public Padaria buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Padaria salvar(Padaria padaria) {
        return repository.save(padaria);
    }

    public Padaria atualizar(Long id, Padaria dados) {
        Padaria padaria = buscarPorId(id);

        if (padaria == null) {
            return null;
        }

        padaria.setNome(dados.getNome());
        padaria.setEndereco(dados.getEndereco());

        return repository.save(padaria);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
