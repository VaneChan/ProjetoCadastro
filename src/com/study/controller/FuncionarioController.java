package com.study.controller;

import com.study.dto.FuncionarioDto;
import com.study.service.impl.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FuncionarioController {
    private final FuncionarioService service;

    @PostMapping(path = "/postFuncionario")
    public ResponseEntity<Void> saveFuncionario(@RequestBody FuncionarioDto funcionarioDto) {

        service.saveFuncionario(funcionarioDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/getFuncionario")
    public ResponseEntity<List<FuncionarioDto>> getAll() {

        List<FuncionarioDto> funcionarioDtoList = service.getAll();

        return ResponseEntity.ok(funcionarioDtoList);
    }
}
