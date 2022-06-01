package com.study.domain.repository;

import com.study.domain.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, String> {

}


