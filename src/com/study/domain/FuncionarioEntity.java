package com.study.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="funcionario")

public class FuncionarioEntity {

    @Id
    @GeneratedValue
    private Long Id;

    @Size(min = 3)
    public String name;

    private int CPF;

    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @NotBlank(message="Conselho nao deve ficar vazio")
    public String conselho;

    @ManyToOne(fetch = FetchType.LAZY)
    private FuncionarioEntity funcionarioEntity;


    }


