package com.cliente.escola.gradecurricular.Materia.service;

import com.cliente.escola.gradecurricular.v1.constant.MensagensConstant;
import com.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.v1.exceptions.MateriaException;
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
import org.springframework.http.HttpStatus;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceExceptionGerericUnitTest {

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
    public void testAtualizarThrowGenericException() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizarMateria(materiaDto);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
    }
    @Test
    public void testCadastrarThrowGenericException() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        materiaEntity.setId(null);

        Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrarMateria(materiaDto);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
        materiaEntity.setId(1L);
    }

    @Test
    public void testConsultarThrowGenericException() {
        Mockito.when(this.materiaRepository.findById(1L)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.consultarMateria(1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testListarThrowGenericException() {
        Mockito.when(this.materiaRepository.findAll()).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, ()->{
            this.materiaService.listarMaterias();
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testListarPorHorarioMinimoThrowGenericException() {
        Mockito.when(this.materiaRepository.findByHorarioMinimo(64)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, ()->{
            this.materiaService.listarMateriasPorHorarioMinimo(64);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByHorarioMinimo(64);
    }

    @Test
    public void testListarPorFrequencihrowGenericException() {
        Mockito.when(this.materiaRepository.findByFrequencia(1)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, ()->{
            this.materiaService.listarMateriasPorFrequencia(1);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByFrequencia(1);
    }

    @Test
    public void testExcluirThrowGenericException(){
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, ()->{
            this.materiaService.excluirMateria(1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).deleteById(1L);
    }
}
