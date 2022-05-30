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
    private String name;
    private String funcionarioId;

    public void saveFuncionario(FuncionarioDto funcionarioDto) {
            FuncionarioEntity funcionarioEntity = new FuncionarioEntity(funcionarioDto.getFuncionarioName(),
                    funcionarioDto.getName());
        repository.save(funcionarioEntity);
    }

    public List<FuncionarioDto> getAll() {
        return
                repository
                        .findAll()
                        .stream()
                        .map(funcionario -> new FuncionarioDto(funcionario.getFuncionarioName(),
                                funcionario.getName()))
                        .collect(Collectors.toList());
    }

    @Override
    public List<FuncionarioDto> getFuncionarioListFromUser(final String Name) {
        checkNotNull(name, "name null");

        final FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(name);

        return repository
                .findAllByUserEntityEquals(funcionarioEntity)
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

            final FuncionarioEntity funcionarioEntity = findFuncionarioById(funcionarioId);

            repository.delete(funcionarioEntity);

        } catch (FuncionarioNotFoundException e) {
            log.error("Funcionario nao encontrado: " + name);
            log.error(e.getMessage());
            throw e;
        } catch (FuncionarioNotFoundException e) {
            log.error("FuncionarioEntity nao encontrado: " + funcionarioId);
            log.error(e.getMessage());
            throw e;
        }
        
    }

    private FuncionarioEntity findFuncionarioById(String funcionarioId) {
    }


    @Override
        public void updateFuncionario(final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException {

        checkNotNull(funcionarioDto);

        FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(funcionarioDto.getName());
        funcionarioEntity = FuncionarioEntity.builder()
                .id(funcionarioDto.Id())
                .description(funcionarioDto.getConselho())
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

        final FuncionarioEntity entity = transformFuncionarioEntity(responseAdviceApi, funcionarioEntity);

        repository.save(entity);

        final FuncionarioDto dto = transformFuncionarioEntity (entity);

        return  dto;
    }

    private FuncionarioDto transformFuncionarioDto(final FuncionarioEntity entity) {
        return
                FuncionarioDto.builder()
                        .id(entity.getId())
                        .conselho(entity.getConselho())
                        .name(entity.getFuncionarioEntity().getName())
                        .build();
    }

    private FuncionarioEntity transformFuncionarioEntity(final ResponseAdviceDto responseApi,
                                                         final FuncionarioEntity funcionarioEntity) {
        return
                FuncionarioEntity.builder()
                        .conselho(responseAdviceAPI.getConselho())
                        .funcionarioEntity(funcionarioEntity)
                        .build();
    }

    private ResponseAdviceDto getAdviceFromAPI() {
        return restTemplate
                .getForEntity("https://api.adviceslip.com/advice", ResponseAdviceDto.class)
                .getBody();
    }

}



