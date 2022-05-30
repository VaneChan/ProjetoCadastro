package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class FuncionarioDto {


    private Long id;
    private String name;
    private int CPF;
    private String email;

    @NotBlank(message="Descricao nao deve ficar vazia")
    private String conselho;



}
