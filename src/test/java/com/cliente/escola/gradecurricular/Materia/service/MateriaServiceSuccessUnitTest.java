package com.cliente.escola.gradecurricular.Materia.service;

import com.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import com.cliente.escola.gradecurricular.v1.services.MateriaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceSuccessUnitTest {

    @Mock
    private IMateriaRepository materiaRepository;

    @InjectMocks
    private MateriaService materiaService;

    private static MateriaEntity materiaEntity;

    @BeforeAll
    public static void init() {
        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setCodigo("ILP");
        materiaEntity.setFrequencia(1);
        materiaEntity.setHoras(64);
        materiaEntity.setNome("Linguagem de programação");
    }

    @Test
    public void testListarSucesso() {
        List<MateriaEntity> lisMateria = new ArrayList<>();
        lisMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findAll()).thenReturn(lisMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listarMaterias();

        Assertions.assertNotNull(listMateriaDto);
        Assertions.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assertions.assertEquals(1, listMateriaDto.get(0).getId());
        Assertions.assertEquals("/v1/materias/1", listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref());
        Assertions.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testPorHorarioMinimoSucesso() {
        List<MateriaEntity> lisMateria = new ArrayList<>();
        lisMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByHorarioMinimo(64)).thenReturn(lisMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listarMateriasPorHorarioMinimo(64);

        Assertions.assertNotNull(listMateriaDto);
        Assertions.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assertions.assertEquals(1, listMateriaDto.get(0).getId());
        Assertions.assertEquals("/v1/materias/1", (listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref()));
        Assertions.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByHorarioMinimo(64);
    }

    @Test
    public void testPorFrequenciaSucesso() {
        List<MateriaEntity> lisMateria = new ArrayList<>();
        lisMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(lisMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listarMateriasPorFrequencia(1);

        Assertions.assertNotNull(listMateriaDto);
        Assertions.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assertions.assertEquals(1, listMateriaDto.get(0).getId());
        Assertions.assertEquals("/v1/materias/1", (listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref()));
        Assertions.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByFrequencia(1);
    }

    @Test
    public void testConsultarSucesso() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        MateriaDto materiaDto = this.materiaService.consultarMateria(1L);

        Assertions.assertNotNull(materiaDto);
        Assertions.assertEquals("ILP", materiaDto.getCodigo());
        Assertions.assertEquals(1, materiaDto.getId());
        Assertions.assertEquals(1, materiaDto.getFrequencia());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testExluirSucesso() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Boolean sucesso = this.materiaService.excluirMateria(1L);

        Assertions.assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, Mockito.times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).deleteById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    @Test
    public void testCadastrarSucesso() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        materiaEntity.setId(null);

        Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.cadastrarMateria(materiaDto);
        Assertions.assertTrue(sucesso);

        Assertions.assertNotNull(materiaDto);
        Assertions.assertEquals("ILP", materiaDto.getCodigo());


        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
        materiaEntity.setId(1L);
    }

    @Test
    public void testAtualizarSucesso() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.atualizarMateria(materiaDto);
        Assertions.assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, Mockito.times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
        materiaEntity.setId(1L);
    }
}
