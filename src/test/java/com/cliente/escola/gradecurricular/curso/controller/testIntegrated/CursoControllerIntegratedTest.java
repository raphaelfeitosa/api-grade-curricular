package com.cliente.escola.gradecurricular.curso.controller.testIntegrated;

import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.v1.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.v1.models.Response;
import com.cliente.escola.gradecurricular.repositories.ICursoRepository;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CursoControllerIntegratedTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Autowired
    private ICursoRepository cursoRepository;

    @BeforeEach
    public void init() {
        this.montarMateriaBaseDeDados();
        this.montarCursoBaseDeDados();
    }

    @AfterEach
    public void finish() {
        this.cursoRepository.deleteAll();
        this.materiaRepository.deleteAll();
    }

    private String montaUri(String urn) {
        return "http://localhost:" + this.port + "/v1/cursos" + urn;
    }

    private void montarMateriaBaseDeDados() {
        MateriaEntity m1 = new MateriaEntity();
        m1.setCodigo("ILP");
        m1.setFrequencia(2);
        m1.setHoras(64);
        m1.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        MateriaEntity m2 = new MateriaEntity();
        m2.setCodigo("POO");
        m2.setFrequencia(2);
        m2.setHoras(84);
        m2.setNome("PROGRAMACAO ORIENTADA A OBJETOS");

        MateriaEntity m3 = new MateriaEntity();
        m3.setCodigo("APA");
        m3.setFrequencia(1);
        m3.setHoras(102);
        m3.setNome("ANALISE E PROJETOS DE ALGORITMOS");

        this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));
    }

    private void montarCursoBaseDeDados() {
        List<MateriaEntity> listMaterias = this.materiaRepository.findAll();

        CursoEntity c1 = new CursoEntity();
        c1.setCodigo("ENGC");
        c1.setNome("ENGENHARIA DA COMPUTACAO");
        c1.setMaterias(listMaterias);

        this.cursoRepository.save(c1);
    }

    @Test
    public void testListarCursos() {
        ResponseEntity<Response<List<GetCursoDto>>> cursos = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri(""), HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<List<GetCursoDto>>>() {
                        });
        Assertions.assertNotNull(cursos.getBody().getData());
        Assertions.assertEquals(1, cursos.getBody().getData().size());
        Assertions.assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    public void testConsultarCursoPorCodigo() {

        ResponseEntity<Response<GetCursoDto>> curso = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri("/codigo-curso?codigo=ENGC"), HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<GetCursoDto>>() {
                        });
        Assertions.assertNotNull(curso.getBody().getData());
        Assertions.assertEquals("ENGENHARIA DA COMPUTACAO", curso.getBody().getData().getNome());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    public void testConsultarCursoPorId() {
        List<CursoEntity> cursosList = this.cursoRepository.findAll();
        Long id = cursosList.get(0).getId();
        ResponseEntity<Response<GetCursoDto>> curso = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri("/"+id.toString()), HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<GetCursoDto>>() {
                        });
        Assertions.assertNotNull(curso.getBody().getData());
        Assertions.assertEquals(id, curso.getBody().getData().getId());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    public void testCadastrarCurso() {
        List<MateriaEntity> listMat = this.materiaRepository.findAll();
        List<Long> idListMat = new ArrayList<>();
        idListMat.add(listMat.get(0).getId());
        idListMat.add(listMat.get(1).getId());

        CursoDto cursoDto = new CursoDto();

        cursoDto.setCodigo("CC");
        cursoDto.setNome("Ciências da computação");
        cursoDto.setMaterias(idListMat);

        HttpEntity<CursoDto> request = new HttpEntity<>(cursoDto);

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri(""), HttpMethod.POST, request,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        List<CursoEntity> listCursoAtualizado = this.cursoRepository.findAll();

        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals(2, listCursoAtualizado.size());
        Assertions.assertEquals(201, curso.getBody().getStatusCode());
    }

    @Test
    public void testAtualizarCurso() {
        List<CursoEntity> cursosList = this.cursoRepository.findAll();
        CursoEntity cursoEntity = cursosList.get(0);

        List<Long> idListMat = new ArrayList<>();
        idListMat.add(cursoEntity.getMaterias().get(0).getId());
        idListMat.add(cursoEntity.getMaterias().get(1).getId());

        CursoDto cursoDto = new CursoDto();

        cursoDto.setId(cursoEntity.getId());
        cursoDto.setCodigo(cursoEntity.getCodigo());
        cursoDto.setNome("Teste Atualiza curso");
        cursoDto.setMaterias(idListMat);

        HttpEntity<CursoDto> request = new HttpEntity<>(cursoDto);

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri(""), HttpMethod.PUT, request,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        Optional<CursoEntity> cursoAtualizado = this.cursoRepository.findById(cursoEntity.getId());

        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals("Teste Atualiza curso", cursoAtualizado.get().getNome());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }

    @Test
    public void testExcluirCursoPorId() {
        List<CursoEntity> materiaList = this.cursoRepository.findAll();
        Long id = materiaList.get(0).getId();

        ResponseEntity<Response<Boolean>> curso = restTemplate.withBasicAuth("root", "root")
                .exchange(this.montaUri("/" + id.toString()), HttpMethod.DELETE,
                        null, new ParameterizedTypeReference<Response<Boolean>>() {
                        });

        List<CursoEntity> listCursoAtualizado = this.cursoRepository.findAll();

        Assertions.assertTrue(curso.getBody().getData());
        Assertions.assertEquals(0, listCursoAtualizado.size());
        Assertions.assertEquals(200, curso.getBody().getStatusCode());
    }
}
