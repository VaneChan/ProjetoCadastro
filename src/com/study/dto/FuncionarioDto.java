package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FuncionarioDto {

    private String name;
    private int CPF;
    private String email;
    @NotBlank(message="Descricao nao deve ficar vazia")
    private String conselho;


}
