package com.cliente.escola.gradecurricular.services;

import com.cliente.escola.gradecurricular.dto.MateriaDto;

import java.util.List;

public interface IMateriaService {
    public Boolean cadastrarMateria(final MateriaDto materiaDto);
    public List<MateriaDto> listarMaterias();
    public MateriaDto consultarMateria(final Long id);
    public Boolean atualizarMateria(final MateriaDto materiaDto);
    public Boolean excluirMateria(final Long id);
    public List<MateriaDto> listarMateriasPorHorarioMinimo(int horaMinima);
    public List<MateriaDto> listarMateriasPorFrequencia(int frequencia);
}
