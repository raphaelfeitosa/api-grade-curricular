package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.dto.CursoDto;
import com.cliente.escola.gradecurricular.dto.GetCursoDto;

import java.util.List;

public interface ICursoService {
    Boolean cadastrarCurso(final CursoDto cursoDto);
    List<GetCursoDto> listarCursos();
    GetCursoDto consultarCursoPorCodigo(String codigo);
    GetCursoDto consultarCursoPorId(Long id);
    Boolean atualizarCurso(final CursoDto cursoDto);
    Boolean excluirCurso(final Long id);
}
