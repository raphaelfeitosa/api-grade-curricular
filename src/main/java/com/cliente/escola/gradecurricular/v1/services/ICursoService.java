package com.cliente.escola.gradecurricular.v1.services;

import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.v1.dto.GetCursoDto;

import java.util.List;

public interface ICursoService {
    Boolean cadastrarCurso(final CursoDto cursoDto);
    List<GetCursoDto> listarCursos();
    GetCursoDto consultarCursoPorCodigo(String codigo);
    GetCursoDto consultarCursoPorId(Long id);
    Boolean atualizarCurso(final CursoDto cursoDto);
    Boolean excluirCurso(final Long id);
}
