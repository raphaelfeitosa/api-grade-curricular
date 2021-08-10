package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.constant.HyperLinkConstant;
import com.cliente.escola.gradecurricular.constant.MensagensConstant;
import com.cliente.escola.gradecurricular.controllers.CursoController;
import com.cliente.escola.gradecurricular.dto.CursoDto;
import com.cliente.escola.gradecurricular.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.exceptions.CursoException;
import com.cliente.escola.gradecurricular.repositories.ICursoRepository;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"cursos"})
@Service
public class CursoService implements ICursoService {

    private final ICursoRepository iCursoRepository;
    private final IMateriaRepository iMateriaRepository;

    @Autowired
    public CursoService(ICursoRepository iCursoRepository, IMateriaRepository iMateriaRepository) {
        this.iCursoRepository = iCursoRepository;
        this.iMateriaRepository = iMateriaRepository;
    }

    @Override
    public Boolean cadastrarCurso(CursoDto cursoDto) {
        try {
            if (cursoDto.getId() != null) {
                throw new CursoException(MensagensConstant.ERRO_ID_INFORMADO.getValor(),
                        HttpStatus.BAD_REQUEST);
            }
            if (cursoDto.getMaterias().isEmpty()) {
                throw new CursoException(MensagensConstant.ERRO_MATERIA_NULL.getValor(),
                        HttpStatus.BAD_REQUEST);
            }
            if (this.iCursoRepository.findByCodigo(cursoDto.getCodigo().toLowerCase()) != null) {
                throw new CursoException(MensagensConstant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(),
                        HttpStatus.BAD_REQUEST);
            }
            return this.cadastrarOuAtualizarCurso(cursoDto);
        } catch (CursoException cursoException) {
            throw cursoException;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Boolean cadastrarOuAtualizarCurso(CursoDto cursoDto) {
        List<MateriaEntity> listMateriaEntity = new ArrayList<>();
        if (!cursoDto.getMaterias().isEmpty() && cursoDto.getMaterias() != null) {
            cursoDto.getMaterias().forEach(materia -> {
                if (this.iMateriaRepository.findById(materia).isPresent()) {
                    listMateriaEntity.add(this.iMateriaRepository.findById(materia).get());
                }
            });
        }
        CursoEntity cursoEntity = new CursoEntity();
        if (cursoDto.getId() != null) {
            cursoEntity.setId(cursoDto.getId());
        }
        cursoEntity.setCodigo(cursoDto.getCodigo());
        cursoEntity.setNome(cursoDto.getNome());
        cursoEntity.setMaterias(listMateriaEntity);

        this.iCursoRepository.save(cursoEntity);
        return Boolean.TRUE;
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<GetCursoDto> listarCursos() {
        try {
            List<CursoEntity> cursoEntityList = this.iCursoRepository.findAll();
            List<GetCursoDto> getAllCursoDto = cursoEntityList.stream().map(GetCursoDto::new).collect(Collectors.toList());
            getAllCursoDto.forEach(cursoDtoResponse ->
                    cursoDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class)
                                    .consultarCursoPorId(cursoDtoResponse.getId()))
                            .withSelfRel()));
            getAllCursoDto.forEach(cursoDtoResponse ->
                    cursoDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class)
                                    .atualizarCurso(new CursoDto()))
                            .withRel(HyperLinkConstant.ATUALIZAR.getValor())));
            getAllCursoDto.forEach(cursoDtoResponse ->
                    cursoDtoResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class)
                                    .consultarCursoPorId(cursoDtoResponse.getId()))
                            .withRel(HyperLinkConstant.EXCLUIR.getValor())));
            return getAllCursoDto;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(key = "#codigo")
    @Override
    public GetCursoDto consultarCursoPorCodigo(String codigo) {
        try {
            CursoEntity cursoEntity = this.iCursoRepository.findByCodigo(codigo.toLowerCase());
            if (cursoEntity == null) {
                throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(),
                        HttpStatus.NOT_FOUND);
            }
            return new GetCursoDto(cursoEntity);
        } catch (CursoException cursoException) {
            throw cursoException;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean atualizarCurso(CursoDto cursoDto) {
        try {
            if (cursoDto.getId() == null) {
                throw new CursoException(MensagensConstant.ERRO_ID_NAO_INFORMADO.getValor(),
                        HttpStatus.BAD_REQUEST);
            }
            this.consultarCursoPorId(cursoDto.getId());
            return this.cadastrarOuAtualizarCurso(cursoDto);
        } catch (CursoException cursoException) {
            throw cursoException;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean excluirCurso(Long id) {
        try {
            if (this.iCursoRepository.findById(id).isPresent()) {
                this.iCursoRepository.deleteById(id);
                return Boolean.TRUE;
            }
            throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(),
                    HttpStatus.NOT_FOUND);
        } catch (CursoException cursoException) {
            throw cursoException;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(key = "#id")
    @Override
    public GetCursoDto consultarCursoPorId(Long id) {
        try {
            Optional<CursoEntity> cursoEntityOptional = this.iCursoRepository.findById(id);
            if (cursoEntityOptional.isEmpty()) {
                throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(),
                        HttpStatus.NOT_FOUND);
            }
            return new GetCursoDto(cursoEntityOptional.get());
        } catch (CursoException cursoException) {
            throw cursoException;
        } catch (Exception exception) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getValor(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
