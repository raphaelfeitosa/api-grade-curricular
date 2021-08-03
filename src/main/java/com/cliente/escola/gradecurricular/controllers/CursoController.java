package com.cliente.escola.gradecurricular.controllers;

import com.cliente.escola.gradecurricular.constant.HyperLinkConstant;
import com.cliente.escola.gradecurricular.dto.CursoDto;
import com.cliente.escola.gradecurricular.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.models.Response;
import com.cliente.escola.gradecurricular.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private ICursoService iCursoService;

    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoDto cursoDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iCursoService.cadastrarCurso(cursoDto));
        response.setStatusCode(HttpStatus.CREATED.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).cadastrarCurso(cursoDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(cursoDto))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<GetCursoDto>>> listarCursos() {
        Response<List<GetCursoDto>> response = new Response<>();
        response.setData(this.iCursoService.listarCursos());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<GetCursoDto>> consultarCursoPorId(@PathVariable Long id) {
        Response<GetCursoDto> response = new Response<>();
        response.setData(this.iCursoService.consultarCursoPorId(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).consultarCursoPorId(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(new CursoDto()))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(id))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/codigo-curso")
    public ResponseEntity<Response<GetCursoDto>> consultarCursoPorCodigo(@RequestParam("codigo") String codigo) {

        Response<GetCursoDto> response = new Response<>();
        GetCursoDto getCursoDto = this.iCursoService.consultarCursoPorCodigo(codigo.toLowerCase());
        response.setData(this.iCursoService.consultarCursoPorCodigo(codigo.toLowerCase()));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).consultarCursoPorCodigo(codigo))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(new CursoDto()))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(getCursoDto.getId()))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> excluirCurso(@PathVariable Long id) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iCursoService.excluirCurso(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoDto cursoDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iCursoService.atualizarCurso(cursoDto));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(cursoDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(cursoDto.getId()))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
