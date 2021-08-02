package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.dto.CursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;

import java.util.List;

public interface ICursoService {
    Boolean cadastrarCurso(final CursoDto cursoDto);
    List<CursoEntity> listarCursos();
    CursoDto consultarCursoPorCodigo(String codigo);
    CursoDto consultarCursoPorId(Long id);
    Boolean atualizarCurso(final CursoDto cursoDto);
    Boolean excluirCurso(final Long id);
}
