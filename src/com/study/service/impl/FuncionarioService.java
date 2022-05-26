package com.study.service.impl;

import com.study.domain.FuncionarioEntity;
import com.study.dto.FuncionarioDto;
import com.study.service.exception.FuncionarioNotFoundException;

import java.util.List;

public interface FuncionarioService {

    List<FuncionarioDto> getFuncionarioListFromUser(String Name);

    void deleteFuncionarioFromUser(String name) throws FuncionarioNotFoundException;


    void updateFuncionario(FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException;

    FuncionarioEntity findFuncionarioByName(String name) throws FuncionarioNotFoundException;

    void saveFuncionario(FuncionarioDto funcionarioDto);

    List<FuncionarioDto> getAll();

    FuncionarioDto generateRandomFuncionario(FuncionarioDto funcionarioDto);


}

