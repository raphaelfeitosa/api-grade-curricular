package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.dto.MateriaDto;

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
