package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.controllers.MateriaController;
import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.exceptions.MateriaException;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = {"materias"})
@Service
public class MateriaService implements IMateriaService {

    private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
    private static final String MATERIA_NAO_ENCONTRADA = "Matéria não encontrada";
    private final IMateriaRepository iMateriaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MateriaService(IMateriaRepository iMateriaRepository) {
        this.modelMapper = new ModelMapper();
        this.iMateriaRepository = iMateriaRepository;
    }

    @Override
    public Boolean cadastrarMateria(MateriaDto materiaDto) {
        try {
            MateriaEntity materiaEntity = this.modelMapper.map(materiaDto, MateriaEntity.class);
            this.iMateriaRepository.save(materiaEntity);
            return Boolean.TRUE;
        } catch (Exception exception) {
            throw new MateriaException(MENSAGEM_ERRO,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listarMaterias() {
        try {
            List<MateriaDto> materiaDto = this.modelMapper.map(this.iMateriaRepository.findAll(),
                    new TypeToken<List<MateriaDto>>() {
                    }.getType());
            materiaDto.forEach(materiaDtoResponse ->
                    materiaDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                            .consultarMateria(materiaDtoResponse.getId()))
                            .withSelfRel()));
            return materiaDto;

        } catch (Exception exception) {
            throw new MateriaException(MENSAGEM_ERRO,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(key = "#id")
    @Override
    public MateriaDto consultarMateria(Long id) {
        try {
            Optional<MateriaEntity> materiaEntityOptional = this.iMateriaRepository.findById(id);
            if (materiaEntityOptional.isPresent()) {
                return this.modelMapper.map(materiaEntityOptional.get(), MateriaDto.class);
            }
            throw new MateriaException(MATERIA_NAO_ENCONTRADA,
                    HttpStatus.NOT_FOUND);
        } catch (MateriaException materiaException) {
            throw materiaException;
        } catch (Exception exception) {
            throw new MateriaException(MENSAGEM_ERRO,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listarMateriasPorHorarioMinimo(int horaMinima) {
        List<MateriaDto> materiaDto = this.modelMapper.map(iMateriaRepository.findByHorarioMinimo(horaMinima),
                new TypeToken<List<MateriaDto>>() {
                }.getType());
        materiaDto.forEach(materiaDtoResponse ->
                materiaDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateria(materiaDtoResponse.getId()))
                        .withSelfRel()));
        return materiaDto;
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listarMateriasPorFrequencia(int frequencia) {
        List<MateriaDto> materiaDto = this.modelMapper.map(iMateriaRepository.findByFrequencia(frequencia),
                new TypeToken<List<MateriaDto>>() {
                }.getType());
        materiaDto.forEach(materiaDtoResponse ->
                materiaDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateria(materiaDtoResponse.getId()))
                        .withSelfRel()));
        return materiaDto;
    }

    @Override
    public Boolean atualizarMateria(MateriaDto materiaDto) {
        try {
            this.consultarMateria(materiaDto.getId());
            MateriaEntity materiaEntityAtualizada = this.modelMapper.map(materiaDto, MateriaEntity.class);
            this.iMateriaRepository.save(materiaEntityAtualizada);
            return Boolean.TRUE;
        } catch (MateriaException materiaException) {
            throw materiaException;
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public Boolean excluirMateria(Long id) {
        try {
            this.consultarMateria(id);
            this.iMateriaRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (MateriaException materiaException) {
            throw materiaException;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
