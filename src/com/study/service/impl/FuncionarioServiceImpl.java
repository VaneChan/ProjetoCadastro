package com.study.service.impl;

import com.study.domain.FuncionarioEntity;
import com.study.domain.repository.FuncionarioRepository;
import com.study.dto.FuncionarioDto;
import com.study.service.exception.FuncionarioNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {


    private final FuncionarioService funcionarioService;
    private final FuncionarioRepository repository;
    //  private final RestTemplate restTemplate;

    @Override
    public List<FuncionarioDto> getFuncionarioListFromUser(final String username) {

        checkNotNull(username, "funcionarioName null");

        final FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(name);

        return repository
                .findAllByUserEntityEquals(funcionarioEntity)
                .stream()
                .map(todoEntity -> new FuncionarioDto(funcionarioEntity.getId(), funcionarioEntity.getUsername(),
                        funcionarioEntity.getEmail()))
                .collect(Collectors.toList());
    }



    @Override
    public void deleteFuncionarioFromUser(final String name)
            throws FuncionarioNotFoundException {
        try {

            checkNotNull(name);
            checkArgument(funcionarioId > 0);

            funcionarioService.findFuncionarioByName(name);

            final FuncionarioEntity funcionarioEntity = findFuncionarioById(funcionarioId);

            repository.delete(funcionarioEntity);

        } catch (FuncionarioNotFoundException e) {
            log.error("Usuario nao encontrado: " + name);
            log.error(e.getMessage());
            throw e;

        }
    }

    private FuncionarioEntity findFuncionarioById(final long funcionarioId) throws FuncionarioNotFoundException {

        checkArgument(funcionarioId > 0);

        return repository
                .findById(funcionarioId)
                .orElseThrow(() -> new FuncionarioNotFoundException("FuncionarioEntity nao encontrado"));
    }

    @Override
    public void updateFuncionario(final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException {

        checkNotNull(funcionarioDto);

        final FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(funcionarioDto.getname());
        final FuncionarioEntity funcionarioEntity = FuncionarioEntity.builder()
                .id(funcionarioDto.getId())
                .description(funcionarioDto.getDescription())
                .funcionarioEntity(funcionarioEntity)
                .build();
        repository.save(funcionarioEntity);

    }



    @Override
    public void saveFuncionario(final FuncionarioDto funcionarioDto) throws FuncionarioNotFoundException {

        checkNotNull(funcionarioDto);

        final FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioByName(funcionarioDto.getname());
        final FuncionarioEntity funcionarioEntity = FuncionarioEntity.builder()
                //          .description(funcionarioDto.getDescription())
                .funcionarioEntity(funcionarioEntity)
                .build();
        repository.save(funcionarioEntity);
    }
    private FuncionarioDto transformFuncionarioDto(final FuncionarioEntity entity) {
        return
                FuncionarioDto.builder()
                        .id(entity.getId())
                        .description(entity.getDescription())
                        .username(entity.getFuncionarioEntity().getName())
                        .build();
    }

    private FuncionarioEntity transformFuncionarioEntity(final ResponseAdviceDto responseAdviceApi,
                                                         final FuncionarioEntity funcionarioEntity) {
        return
                FuncionarioEntity.builder()
                        .description(responseAdviceApi.getAdvice())
                        .funcionarioEntity(funcionarioEntity)
                        .build();
    }

    private ResponseAdviceDto getAdviceFromAPI() {
        return restTemplate
                .getForEntity("https://api.adviceslip.com/advice", ResponseAdviceDto.class)
                .getBody();
    }

}


