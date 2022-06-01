package com.study.service.impl;

import com.study.domain.FuncionarioEntity;
import com.study.domain.repository.FuncionarioRepository;
import com.study.dto.FuncionarioDto;
import com.study.dto.ResponseAdviceDto;
import com.study.service.exception.FuncionarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Log4j2
@RequiredArgsConstructor

public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioService funcionarioService;
    private final FuncionarioRepository repository;
    private final RestTemplate restTemplate;

    public void saveFuncionario(FuncionarioDto funcionarioDto) {

        checkNotNull(funcionarioDto);

        FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(funcionarioDto.getName());
        funcionarioEntity = FuncionarioEntity.builder()
                .conselho(funcionarioDto.getConselho())
                .funcionarioEntity(funcionarioEntity)
                .build();
        repository.save(funcionarioEntity);
    }
    public List<FuncionarioDto> getAll() {
        return
                repository
                        .findAll()
                        .stream()
                        .map(funcionario -> transformFuncionarioEntity (funcionario))
                        .collect(Collectors.toList());
    }

    @Override
    public List<FuncionarioDto> getFuncionarioListFromUser(final String name) {
        checkNotNull( name, "name null");

        final FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(name);

        return repository
                .findAll()
                .stream()
                .map(FuncionarioEntity -> {
                    return new FuncionarioDto(funcionarioEntity.getId(), funcionarioEntity.getName(),
                            funcionarioEntity.getCPF(), funcionarioEntity.getEmail(),funcionarioEntity.getConselho());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFuncionarioFromUser(final String name, final long FuncionarioId) throws
            FuncionarioNotFoundException {
        try {

            checkNotNull(name);
            checkArgument(FuncionarioId > 0);

            funcionarioService.findFuncionarioByName(name);
            final FuncionarioEntity funcionarioEntity = findFuncionarioByName(name);

            repository.delete(funcionarioEntity);

        } catch (FuncionarioNotFoundException e) {
            log.error("Funcionario nao encontrado: " + name);
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
        public void updateFuncionario(final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException {

        checkNotNull(funcionarioDto);

        FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(funcionarioDto.getName());
        funcionarioEntity = FuncionarioEntity.builder()
                .Id(funcionarioDto.getId())
                .conselho(funcionarioDto.getConselho())
                .funcionarioEntity(funcionarioEntity)
                .build();
        repository.save(funcionarioEntity);

    }


    public FuncionarioEntity findFuncionarioByName(String name) throws FuncionarioNotFoundException{
        return repository
                .findById(name)
                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionario nao encontrado."));
    }

    public FuncionarioDto generateRandomFuncionario(final FuncionarioDto funcionarioDto) {

        checkNotNull(funcionarioDto);

        final ResponseAdviceDto responseAdviceApi = getAdviceFromAPI();

        FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName (funcionarioDto.getName());
        if(funcionarioEntity!=null){
            funcionarioEntity.setConselho(responseAdviceApi.getConselho());
        }
        else {
            funcionarioEntity= FuncionarioEntity.builder()
                    .Id(funcionarioDto.getId())
                    .name(funcionarioDto.getName ())
                    .CPF(funcionarioDto.getCPF())
                    .email(funcionarioDto.getEmail())
                    .conselho(responseAdviceApi.getConselho())
                    .build();

        }



        repository.save(funcionarioEntity);

        final FuncionarioDto dto;
        dto = transformFuncionarioEntity(responseAdviceApi, funcionarioEntity);

        return  dto;
    }

    private FuncionarioDto transformFuncionarioEntity(final ResponseAdviceDto responseApi,
                                                      final FuncionarioEntity funcionarioEntity) {

        return

                FuncionarioDto.builder()
                        .Id(funcionarioEntity.getId())
                        .name(funcionarioEntity.getName ())
                        .CPF(funcionarioEntity.getCPF())
                        .email(funcionarioEntity.getEmail())
                        .conselho(responseApi.getConselho())
                        .build();
    }
    private FuncionarioDto transformFuncionarioEntity(final FuncionarioEntity funcionarioEntity)
    {

        return

                FuncionarioDto.builder()
                        .Id(funcionarioEntity.getId())
                        .name(funcionarioEntity.getName ())
                        .CPF(funcionarioEntity.getCPF())
                        .email(funcionarioEntity.getEmail())
                        .conselho(funcionarioEntity.getConselho())
                        .build();
    }

    private ResponseAdviceDto getAdviceFromAPI() {
        return restTemplate
                .getForEntity("https://api.adviceslip.com/advice", ResponseAdviceDto.class)
                .getBody();
    }

}



