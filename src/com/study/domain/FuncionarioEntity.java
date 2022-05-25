package com.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="funcionario")

public class FuncionarioEntity {

    @Id
    private Long id;

    @Size(min = 3)
    public String name;

    private int CPF;

    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "funcionarioEntity")
    private List<FuncionarioEntity> FuncionarioEntityList;


    }

