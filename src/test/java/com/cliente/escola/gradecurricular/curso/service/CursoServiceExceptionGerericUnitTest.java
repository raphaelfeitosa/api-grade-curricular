package com.cliente.escola.gradecurricular.curso.service;

import com.cliente.escola.gradecurricular.v1.constant.MensagensConstant;
import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.v1.exceptions.CursoException;
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
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CursoServiceExceptionGerericUnitTest {
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
    public void testCadastrarThrowGenericException() {
        List<Long> listId = new ArrayList<Long>();
        listId.add(1L);

        CursoDto cursoDto = new CursoDto();
        cursoDto.setCodigo("CC");
        cursoDto.setNome("Ciencias da computacao");
        cursoDto.setMaterias(listId);

        CursoEntity novoCursoEntity = new CursoEntity();
        novoCursoEntity.setCodigo("CC");
        novoCursoEntity.setMaterias(new ArrayList<>());
        novoCursoEntity.setNome("Ciencias da computacao");

        Mockito.when(this.cursoRepository.findByCodigo("cc")).thenReturn(null);
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(this.cursoRepository.save(novoCursoEntity)).thenThrow(IllegalStateException.class);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.cadastrarCurso(cursoDto);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findByCodigo("cc");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(1)).save(novoCursoEntity);
    }
    @Test
    public void testListarThrowException() {
        Mockito.when(this.cursoRepository.findAll()).thenThrow(IllegalStateException.class);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, ()->{
            this.cursoService.listarCursos();
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testAtualizarThrowGenericException() {
        List<Long> listMateriaId = new ArrayList<Long>();
        listMateriaId.add(1L);

        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(1L);
        cursoDto.setCodigo("CC");
        cursoDto.setNome("CIENCIAS DA COMPUTACAO");
        cursoDto.setMaterias(listMateriaId);

        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.cursoRepository.save(cursoEntity)).thenThrow(IllegalStateException.class);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.atualizarCurso(cursoDto);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(2)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(1)).save(cursoEntity);
    }

    @Test
    public void testConsultarPorCodigoThrowGenericException() {
        Mockito.when(this.cursoRepository.findByCodigo("CC")).thenThrow(IllegalStateException.class);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.consultarCursoPorCodigo("cc");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findByCodigo("cc");
    }

    @Test
    public void testConsultarPorIdThrowGenericException() {
        Mockito.when(this.cursoRepository.findById(1L)).thenThrow(IllegalStateException.class);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.consultarCursoPorId(1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testExcluirThrowGenericException() {
        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
        Mockito.doThrow(IllegalStateException.class).when(this.cursoRepository).deleteById(1L);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.excluirCurso(1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(1)).deleteById(1L);
    }
}
