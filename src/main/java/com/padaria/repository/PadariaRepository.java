package com.padaria.repository;

import com.padaria.model.Padaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PadariaRepository extends JpaRepository<Padaria, Long> {
}
