package com.study.service.impl;

import com.study.dto.FuncionarioDto;
import com.study.service.exception.FuncionarioNotFoundException;

import java.util.List;

public interface FuncionarioService {

    List<FuncionarioDto> getFuncionarioListFromUser(String userName);

    void deleteFuncionarioFromUser(String funcionarioName) throws FuncionarioNotFoundException;

    void updateTodo(FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException;

    void findFuncionarioByName(String name);

    void saveFuncionario(FuncionarioDto funcionarioDto);

    //  TodoDto generateRandomTodo(UserDto userDto);
}

