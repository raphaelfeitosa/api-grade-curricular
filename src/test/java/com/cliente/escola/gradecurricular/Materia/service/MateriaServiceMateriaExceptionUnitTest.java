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
public class MateriaServiceMateriaExceptionUnitTest {

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
    public void testCadastrarComIdThrowMateriaException() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrarMateria(materiaDto);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_ID_INFORMADO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }
    @Test
    public void testCadastrarComCodigoExistenteThrowMateriaException() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(materiaEntity);

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrarMateria(materiaDto);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    @Test
    public void testAtualizarThrowMateriaException() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizarMateria(materiaDto);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    @Test
    public void testExcluirThrowMateriaException() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;

        materiaException = Assertions.assertThrows(MateriaException.class, () -> {
            this.materiaService.excluirMateria(1L);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        Assertions.assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).deleteById(1L);
    }
}
