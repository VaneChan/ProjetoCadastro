package com.study.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="funcionario")

public class FuncionarioEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3)
    public String name;

    private int CPF;

    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @NotBlank(message="Conselho nao deve ficar vazio")
    private String conselho;

    @ManyToOne(fetch = FetchType.LAZY)
    private FuncionarioEntity funcionarioEntity;


    }


