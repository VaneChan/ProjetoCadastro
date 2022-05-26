package com.study.controller;


import com.study.dto.FuncionarioDto;
import com.study.service.exception.FuncionarioNotFoundException;
import com.study.service.impl.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FuncionarioController {
    private final FuncionarioService service;

    @GetMapping(path="/funcionarios/{name}")
    public ResponseEntity<List<FuncionarioDto>> getFuncionarioListFromUser(@PathVariable(name="name") final String name)
            throws FuncionarioNotFoundException {

        final List<FuncionarioDto> funcionarioDtoList =  service.getFuncionarioListFromUser(name);

        return ResponseEntity.ok(funcionarioDtoList);
    }

    @DeleteMapping(path="/funcionarios/{name}/{Id}")
    public ResponseEntity<Void> deleteFuncionarioFromUser(@PathVariable(name="name") final String name,
                                                   @PathVariable(name="Id") final long Id)
            throws FuncionarioNotFoundException {

        service.deleteFuncionarioFromUser(name, Id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path="/funcionarios")
    public ResponseEntity<Void> updateFuncionario(@RequestBody final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException
    {
        service.updateFuncionario(funcionarioDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping(path="/funcionarios")
    public ResponseEntity<Void> saveFuncionario(@RequestBody final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException
    {
        service.saveFuncionario(funcionarioDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path="/random")
    public ResponseEntity<FuncionarioDto> saveRandomFuncionarioDto(@RequestBody final FuncionarioDto funcionarioDto) {

        final FuncionarioDto dto = service.generateRandomFuncionario(funcionarioDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
