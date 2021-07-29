package com.cliente.escola.gradecurricular.controllers;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.models.Response;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import com.cliente.escola.gradecurricular.services.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {
    private static final String DELETE = "DELETE";
    private static final String UPDATE = "UPDATE";
    private static final String LIST = "GET_ALL";

    @Autowired
    private IMateriaService iMateriaService;

    @GetMapping
    public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
        Response<List<MateriaDto>> response = new Response<>();
        response.setData(this.iMateriaService.listarMaterias());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MateriaDto>> consultarMateria(@PathVariable Long id) {
        Response<MateriaDto> response = new Response<>();
        MateriaDto materiaDto = this.iMateriaService.consultarMateria(id);
        response.setData(this.iMateriaService.consultarMateria(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withRel(UPDATE));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withRel(DELETE));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/horario-minimo/{horaMinima}")
    public ResponseEntity<Response<List<MateriaDto>>> listarMateriaPorHoraMinima(@PathVariable int horaMinima) {
        Response<List<MateriaDto>> response = new Response<>();
        List<MateriaDto> materiaDto = this.iMateriaService.listarMateriasPorHorarioMinimo(horaMinima);
        response.setData(materiaDto);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMateriaPorHoraMinima(horaMinima))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/frequencia/{frequencia}")
    public ResponseEntity<Response<List<MateriaDto>>> listarMateriasPorFrequencia(@PathVariable int frequencia) {
        Response<List<MateriaDto>> response = new Response<>();
        List<MateriaDto> materiaDto = this.iMateriaService.listarMateriasPorFrequencia(frequencia);
        response.setData(materiaDto);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMateriasPorFrequencia(frequencia))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.cadastrarMateria(materiaDto));
        response.setStatusCode(HttpStatus.CREATED.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).cadastrarMateria(materiaDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withRel(UPDATE));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(LIST));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.atualizarMateria(materiaDto));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(LIST));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.excluirMateria(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(LIST));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
