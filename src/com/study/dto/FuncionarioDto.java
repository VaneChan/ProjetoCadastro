package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class FuncionarioDto {


    private Long Id;
    private String name;
    private int CPF;
    private String email;
    public String conselho;


}
