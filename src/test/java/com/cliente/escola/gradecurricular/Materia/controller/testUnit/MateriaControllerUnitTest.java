package com.cliente.escola.gradecurricular.Materia.controller.testUnit;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.models.Response;
import com.cliente.escola.gradecurricular.services.IMateriaService;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MateriaControllerUnitTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IMateriaService iMateriaService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static MateriaDto materiaDto;

    @BeforeAll
    public static void init() {
        materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("Linguagem de programação");
    }

    private String montarUri(String urn) {
        return "http://localhost:" + this.port + "/materias" + urn;
    }

    @Test
    @DisplayName("Teste para listar materias")
    void testListarMaterias() {
        List<MateriaDto> listMaterias = new ArrayList<>();
        listMaterias.add(materiaDto);

        Mockito.when(this.iMateriaService.listarMaterias()).thenReturn(listMaterias);
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.exchange(
                this.montarUri(""), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para consultar uma materia")
    void testConsultarMaterias() {
        Mockito.when(this.iMateriaService.consultarMateria(1L)).thenReturn(materiaDto);
        ResponseEntity<Response<MateriaDto>> materias = restTemplate.exchange(
                this.montarUri("/1"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<MateriaDto>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para cadastrar uma materia")
    void testCadastrarMaterias() {
        Mockito.when(this.iMateriaService.cadastrarMateria(materiaDto)).thenReturn(Boolean.TRUE);
        HttpEntity<MateriaDto> request = new HttpEntity<>(materiaDto);
        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                this.montarUri(""), HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para atualizar uma materia")
    void testAtualizarMaterias() {
        Mockito.when(this.iMateriaService.atualizarMateria(materiaDto)).thenReturn(Boolean.TRUE);
        HttpEntity<MateriaDto> request = new HttpEntity<>(materiaDto);
        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                this.montarUri(""), HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para excluir uma materia")
    void testExcluirMaterias() {
        Mockito.when(this.iMateriaService.excluirMateria(1L)).thenReturn(Boolean.TRUE);
        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                this.montarUri("/1"), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para retornar uma lista de materias com horario minimo")
    void testlistarMateriasPorHoraMinima() {
        Mockito.when(this.iMateriaService.listarMateriasPorHorarioMinimo(64)).thenReturn((new ArrayList<MateriaDto>()));
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.exchange(
                this.montarUri("/horario-minimo/64"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para retornar uma lista de materias por frequencia")
    void testlistarMateriasPorFrequencia() {
        Mockito.when(this.iMateriaService.listarMateriasPorFrequencia(1)).thenReturn((new ArrayList<MateriaDto>()));
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.exchange(
                this.montarUri("/frequencia/1"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }
}
