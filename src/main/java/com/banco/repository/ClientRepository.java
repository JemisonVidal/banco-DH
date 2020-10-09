package com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
