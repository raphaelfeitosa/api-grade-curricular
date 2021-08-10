package com.cliente.escola.gradecurricular.Materia.controller.testIntegrated;

import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import com.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.cliente.escola.gradecurricular.v1.models.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MateriaControllerIntegratedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private IMateriaRepository iMateriaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init() {
        this.montarBaseDeDados();
    }

    @AfterEach
    public void finish() {
        this.iMateriaRepository.deleteAll();
    }

    private String montarUri(String urn) {
        return "http://localhost:" + this.port + "/v1/materias" + urn;
    }

    private void montarBaseDeDados() {
        MateriaEntity materiaEntity1 = new MateriaEntity();
        materiaEntity1.setCodigo("ILP");
        materiaEntity1.setFrequencia(2);
        materiaEntity1.setHoras(64);
        materiaEntity1.setNome("INTRODUÇÃO A lINGUAGME DE PROGRAMAÇÃO");

        MateriaEntity materiaEntity2 = new MateriaEntity();
        materiaEntity2.setCodigo("POO");
        materiaEntity2.setFrequencia(2);
        materiaEntity2.setHoras(84);
        materiaEntity2.setNome("PROGRAMAÇÃO ORIENTADA A OBJETOS");

        MateriaEntity materiaEntity3 = new MateriaEntity();
        materiaEntity3.setCodigo("APA");
        materiaEntity3.setFrequencia(1);
        materiaEntity3.setHoras(102);
        materiaEntity3.setNome("ANALISE E PROJETOS DE ALGORITMOS");

        this.iMateriaRepository.saveAll(Arrays.asList(materiaEntity1, materiaEntity2, materiaEntity3));
    }

    @Test
    @DisplayName("Teste para listar materias")
    void testListarMaterias() {
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri(""), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(3, materias.getBody().getData().size());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para retornar uma lista de materias com horario minimo")
    void testlistarMateriasPorHoraMinima() {
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri("/horario-minimo/80"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(2, materias.getBody().getData().size());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para retornar uma lista de materias por frequencia")
    void testlistarMateriasPorFrequencia() {
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri("/frequencia/1"), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(1, materias.getBody().getData().size());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para consultar uma materia")
    void testConsultarMateriaPorId() {
        List<MateriaEntity> materiaEntityList = this.iMateriaRepository.findAll();
        Long id = materiaEntityList.get(0).getId();
        ResponseEntity<Response<MateriaDto>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri("/" + Long.toString(id)), HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<MateriaDto>>() {
                });
        Assertions.assertNotNull(materias.getBody().getData());
        Assertions.assertEquals(id, materias.getBody().getData().getId());
        Assertions.assertEquals(64, materias.getBody().getData().getHoras());
        Assertions.assertEquals("ILP", materias.getBody().getData().getCodigo());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para cadastrar uma materia")
    void testCadastrarMateria() {
        MateriaEntity materiaEntity4 = new MateriaEntity();
        materiaEntity4.setCodigo("CALC");
        materiaEntity4.setFrequencia(2);
        materiaEntity4.setHoras(80);
        materiaEntity4.setNome("CALCULO I");

        HttpEntity<MateriaEntity> request = new HttpEntity<>(materiaEntity4);
        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri(""), HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        List<MateriaEntity> listMateriaEntityCadastrada = this.iMateriaRepository.findAll();
        Assertions.assertTrue(materias.getBody().getData());
        Assertions.assertEquals(4, listMateriaEntityCadastrada.size());
        Assertions.assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para atualizar uma materia")
    void testAtualizarMateria() {
        List<MateriaEntity> materiaEntityList = this.iMateriaRepository.findAll();
        MateriaEntity materiaEntity = materiaEntityList.get(0);
        materiaEntity.setNome("Teste Atualiza Materia");
        HttpEntity<MateriaEntity> request = new HttpEntity<>(materiaEntity);
        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri(""), HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        MateriaEntity materiaEntityAtualizada = this.iMateriaRepository.findById(materiaEntity.getId()).get();
        Assertions.assertTrue(materias.getBody().getData());
        Assertions.assertEquals("Teste Atualiza Materia", materiaEntityAtualizada.getNome());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Teste para excluir materia por id")
    void testExluirMateriaPorId() {
        List<MateriaEntity> materiaEntityList = this.iMateriaRepository.findAll();
        Long id = materiaEntityList.get(0).getId();
        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("root", "root").exchange(
                this.montarUri("/" + id), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });

        List<MateriaEntity> listMateriaEntityAtualizada = this.iMateriaRepository.findAll();
        Assertions.assertTrue(materias.getBody().getData());
        Assertions.assertEquals(2, listMateriaEntityAtualizada.size());
        Assertions.assertEquals(200, materias.getBody().getStatusCode());
    }
}
