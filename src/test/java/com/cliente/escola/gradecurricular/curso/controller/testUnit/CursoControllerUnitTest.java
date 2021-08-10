package com.cliente.escola.gradecurricular.curso.controller.testUnit;

import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.v1.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.v1.models.Response;
import com.cliente.escola.gradecurricular.v1.services.ICursoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CursoControllerUnitTest {
    @LocalServerPort
    private int port;

    @MockBean
    private ICursoService cursoService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static CursoDto cursoDto;
    private static GetCursoDto getCursoDto;
    private static CursoEntity cursoEntity;
    private static MateriaEntity materiaEntity;

    @BeforeAll
    public static void init() {
        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setCodigo("ILP");
        materiaEntity.setFrequencia(1);
        materiaEntity.setHoras(64);
        materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        List<MateriaEntity> materias = new ArrayList<>();
        materias.add(materiaEntity);

        List<Long> listMatateria = new ArrayList<>();
        listMatateria.add(1L);

        cursoDto = new CursoDto();
        cursoDto.setId(1L);
        cursoDto.setCodigo("ENGCP");
        cursoDto.setNome("ENGENHARIA DA COMPUTAÇÃO");
        cursoDto.setMaterias(listMatateria);

        getCursoDto = new GetCursoDto();
        getCursoDto.setId(1L);
        getCursoDto.setCodigo("ENGCP");
        getCursoDto.setNome("ENGENHARIA DA COMPUTAÇÃO");
        getCursoDto.setMaterias(materias);

        cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
        cursoEntity.setCodigo("ENGCP");
        cursoEntity.setNome("ENGENHARIA DA COMPUTAÇÃO");
        cursoEntity.setMaterias(materias);
    }

    private String montaUri(String urn) {
        return "http://localhost:" + this.port + "/v1/cursos" + urn;
    }

    @Test
    @DisplayName("Teste para listar cursos")
    public void testListarCursos() {
        Mockito.when(this.cursoService.listarCursos()).thenReturn(Collections.singletonList(getCursoDto));

        ResponseEntity<Response<List<GetCursoDto>>> cursos = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri(""), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        System.out.println(cursos);
        Assertions.assertNotNull(cursos.getBody().getData());
        Assertions.assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para consultar uma curso por codigo")
    public void testConsultarPorCodigoCurso() {
        Mockito.when(this.cursoService.consultarCursoPorCodigo("ENGCOMP")).thenReturn(getCursoDto);

        ResponseEntity<Response<GetCursoDto>> curso = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri("/codigo-curso?codigo=ENGCOMP"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<GetCursoDto>>() {
                });
        Assertions.assertNotNull(curso.getBody().getData());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para consultar uma curso por id")
    public void testConsultarPorIdCurso() {
        Mockito.when(this.cursoService.consultarCursoPorId(1L)).thenReturn(getCursoDto);

        ResponseEntity<Response<GetCursoDto>> curso = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri("/1"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<GetCursoDto>>() {
                });
        Assertions.assertNotNull(curso.getBody().getData());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para cadastrar curso")
    public void testCadastrarCurso() {
        Mockito.when(this.cursoService.cadastrarCurso(cursoDto)).thenReturn(Boolean.TRUE);

        cursoDto.setId(null);

        HttpEntity<CursoDto> request = new HttpEntity<>(cursoDto);

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri(""), HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals(201, curso.getBody().getStatusCode());
        cursoDto.setId(1L);
    }

    @Test
    @DisplayName("Teste para atualizar curso")
    public void testAtualizarCurso() {
        Mockito.when(this.cursoService.atualizarCurso(cursoDto)).thenReturn(Boolean.TRUE);

        HttpEntity<CursoDto> request = new HttpEntity<>(cursoDto);

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri(""), HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para excluir curso")
    public void testExcluirCurso() {
        Mockito.when(this.cursoService.excluirCurso(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root").exchange(
                this.montaUri("/1"), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }
}
