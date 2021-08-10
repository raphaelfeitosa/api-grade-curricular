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
public class CursoServiceCursoExceptionUnitTest {
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
    public void testCadastrarComIdThrowCursoException() {
        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(1L);
        cursoDto.setCodigo("CC");
        cursoDto.setNome("ENGENHARIA DA COMPUTACAO");

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.cadastrarCurso(cursoDto);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_ID_INFORMADO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(0)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(0)).save(cursoEntity);
    }

    @Test
    public void testCadastrarComCodigoExistenteThrowCursoException() {
        List<Long> listMateriaId = new ArrayList<Long>();
        listMateriaId.add(1L);

        CursoDto cursoDto = new CursoDto();
        cursoDto.setCodigo("CC");
        cursoDto.setNome("ENGENHARIA DA COMPUTACAO");
        cursoDto.setMaterias(listMateriaId);
        Mockito.when(this.cursoRepository.findByCodigo("cc")).thenReturn(cursoEntity);

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.cadastrarCurso(cursoDto);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findByCodigo("cc");
        Mockito.verify(this.cursoRepository, Mockito.times(0)).save(cursoEntity);

    }

    @Test
    public void testAtualizarThrowCursoException() {
        List<Long> listMateriaId = new ArrayList<Long>();
        listMateriaId.add(1L);

        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(1L);
        cursoDto.setCodigo("CC");
        cursoDto.setNome("ENGENHARIA DA COMPUTACAO");
        cursoDto.setMaterias(listMateriaId);

        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.empty());

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.atualizarCurso(cursoDto);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(0)).save(cursoEntity);
    }

    @Test
    public void testExcluirThrowCursoException() {
        Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.empty());

        CursoException cursoException;

        cursoException = Assertions.assertThrows(CursoException.class, () -> {
            this.cursoService.excluirCurso(1L);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, cursoException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), cursoException.getMessage());

        Mockito.verify(this.cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.cursoRepository, Mockito.times(0)).deleteById(1L);

    }
}
