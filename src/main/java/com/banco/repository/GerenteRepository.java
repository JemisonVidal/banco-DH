package com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.model.Gerente;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {

}
