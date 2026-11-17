package com.bn.funcionario.services;

import com.bn.funcionario.models.FuncionarioModel;
import com.bn.funcionario.repositories.FuncionarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FuncionarioServices {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServices(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<FuncionarioModel> findAll() {
        return funcionarioRepository.findAll();
    }

    public FuncionarioModel buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));
    }

    public FuncionarioModel criarPessoa(FuncionarioModel funcionario) {
        funcionario.setId(null);
        return funcionarioRepository.save(funcionario);
    }

    public FuncionarioModel atualizar(Long id, FuncionarioModel dados) {
        FuncionarioModel funcionario = buscarPorId(id);
        funcionario.setNome(dados.getNome());
        funcionario.setCpf(dados.getCpf());
        funcionario.setRua(dados.getRua());
        return funcionarioRepository.save(funcionario);
    }

    public void deletar(Long id) {
        funcionarioRepository.delete(buscarPorId(id));
    }
}
