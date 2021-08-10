package com.cliente.escola.gradecurricular.v1.controllers;

import com.cliente.escola.gradecurricular.config.SwaggerConfig;
import com.cliente.escola.gradecurricular.v1.constant.HyperLinkConstant;
import com.cliente.escola.gradecurricular.v1.dto.CursoDto;
import com.cliente.escola.gradecurricular.v1.dto.GetCursoDto;
import com.cliente.escola.gradecurricular.v1.models.Response;
import com.cliente.escola.gradecurricular.v1.services.ICursoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.CURSO)
@RestController
@RequestMapping("/v1/cursos")
public class CursoController {

    @Autowired
    private ICursoService iCursoService;

    @ApiOperation(value = "Cadastrar um novo curso")
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "Entidade criada com sucesse"),
            @ApiResponse(code=400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code=500, message = "Erro interno no serviço")
    })
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

    @ApiOperation(value = "Listar todos os cursos")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Lista de entidades exibidas com sucesso"),
            @ApiResponse(code=500, message = "Erro interno no serviço"),
    })
    @GetMapping
    public ResponseEntity<Response<List<GetCursoDto>>> listarCursos() {
        Response<List<GetCursoDto>> response = new Response<>();
        response.setData(this.iCursoService.listarCursos());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Consultar curso por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curso encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Curso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
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

    @ApiOperation(value = "Consultar curso por código")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curso encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Curso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/codigo-curso")
    public ResponseEntity<Response<GetCursoDto>> consultarCursoPorCodigo(@RequestParam("codigo") String codigo) {

        Response<GetCursoDto> response = new Response<>();
        GetCursoDto getCursoDto = this.iCursoService.consultarCursoPorCodigo(codigo);
        response.setData(this.iCursoService.consultarCursoPorCodigo(codigo));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).consultarCursoPorCodigo(codigo))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(new CursoDto()))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(getCursoDto.getId()))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Excluir um curso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curso excluído com sucesso"),
            @ApiResponse(code = 404, message = "Curso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
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

    @ApiOperation(value = "Atualizar um curso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Curso atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 404, message = "Curso não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
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
