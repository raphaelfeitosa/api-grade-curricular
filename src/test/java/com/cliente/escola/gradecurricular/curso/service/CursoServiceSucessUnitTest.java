package com.cliente.escola.gradecurricular.curso.service;

import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.v1.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.repositories.ICursoRepository;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import com.cliente.escola.gradecurricular.v1.services.CursoService;
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
public class CursoServiceSucessUnitTest {
    @Mock
    private IMateriaRepository materiaRepository;

    @Mock
    private ICursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private static MateriaEntity materiaEntity;

    private static CursoEntity cursoEntity;

    @BeforeAll
    public static void init() {

        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setCodigo("ILP");
        materiaEntity.setFrequencia(1);
        materiaEntity.setHoras(64);
        materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        List<MateriaEntity> listMatateria = new ArrayList<>();
        listMatateria.add(materiaEntity);

        cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setCodigo("CC");
        cursoEntity.setNome("CIENCIAS DA COMPUTACAO");
        cursoEntity.setMaterias(listMatateria);
    }

    @Test
    public void testCadastrarSucesso() {
        List<Long> listMateriaId = new ArrayList<Long>();
        listMateriaId.add(1L);

        CursoEntity novoCursoEntity = new CursoEntity();
        novoCursoEntity.setCodigo("CC");
        novoCursoEntity.setMaterias(new ArrayList<>());
        novoCursoEntity.setNome("ENGENHARIA DA COMPUTACAO");

        CursoDto cursoDto = new CursoDto();
        cursoDto.setCodigo("CC");
        cursoDto.setNome("ENGENHARIA DA COMPUTACAO");
        cursoDto.setMaterias(listMateriaId);

        Mockito.when(this.cursoRepository.findByCodigo("cc")).thenReturn(null);
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(this.cursoRepository.save(novoCursoEntity)).thenReturn(novoCursoEntity);

        Boolean sucesso = this.cursoService.cadastrarCurso(cursoDto);

        Assertions.assertTrue(sucesso);

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findByCodigo("cc");
        Mockito.verify(this.cursoRepository, Mockito.times(1)).save(novoCursoEntity);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testAtualizarSucesso() {
        List<Long> listMateriaId = new ArrayList<Long>();
        listMateriaId.add(1L);

        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(1L);
        cursoDto.setCodigo("CC");
        cursoDto.setNome("ENGENHARIA DA COMPUTACAO");
        cursoDto.setMaterias(listMateriaId);

        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
//        Mockito.when(this.cursoRepository.save(cursoEntity)).thenReturn(cursoEntity);

        Boolean sucesso = this.cursoService.atualizarCurso(cursoDto);

        Assertions.assertTrue(sucesso);

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
//        Mockito.verify(this.cursoRepository, Mockito.times(1)).save(cursoEntity);
        Mockito.verify(this.materiaRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    public void testListarSucesso() {
        List<CursoEntity> listCurso = new ArrayList<>();
        listCurso.add(cursoEntity);

        Mockito.when(this.cursoRepository.findAll()).thenReturn(listCurso);

        List<GetCursoDto> listCursoDto = this.cursoService.listarCursos();
        Assertions.assertNotNull(listCursoDto);
        Assertions.assertEquals("CC", listCursoDto.get(0).getCodigo());
        Assertions.assertEquals(1, listCursoDto.get(0).getId());
        Assertions.assertEquals(1, listCursoDto.size());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testConsultarSucesso() {
        Mockito.when(this.cursoRepository.findByCodigo("cc")).thenReturn(cursoEntity);
        GetCursoDto getCursoDto = this.cursoService.consultarCursoPorCodigo("cc");

        Assertions.assertNotNull(cursoEntity);
        Assertions.assertEquals("CC", cursoEntity.getCodigo());
        Assertions.assertEquals(1, cursoEntity.getId());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findByCodigo("cc");
    }

    @Test
    public void testExcluirSucesso() {
        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
        Boolean sucesso = this.cursoService.excluirCurso(1L);

        Assertions.assertTrue(sucesso);

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(1)).deleteById(1L);
    }
}
