package com.cliente.escola.gradecurricular.v1.services;

import com.cliente.escola.gradecurricular.v1.dto.MateriaDto;

import java.util.List;

public interface IMateriaService {
     Boolean cadastrarMateria(final MateriaDto materiaDto);
     List<MateriaDto> listarMaterias();
     MateriaDto consultarMateria(final Long id);
     Boolean atualizarMateria(final MateriaDto materiaDto);
     Boolean excluirMateria(final Long id);
     List<MateriaDto> listarMateriasPorHorarioMinimo(int horaMinima);
     List<MateriaDto> listarMateriasPorFrequencia(int frequencia);
}
