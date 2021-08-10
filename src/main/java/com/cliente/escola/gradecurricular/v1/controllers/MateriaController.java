package com.cliente.escola.gradecurricular.v1.controllers;

import com.cliente.escola.gradecurricular.config.SwaggerConfig;
import com.cliente.escola.gradecurricular.v1.constant.HyperLinkConstant;
import com.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.cliente.escola.gradecurricular.v1.models.Response;
import com.cliente.escola.gradecurricular.v1.services.IMateriaService;
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

@Api(tags = SwaggerConfig.MATERIA)
@RestController
@RequestMapping("/v1/materias")
public class MateriaController {

    @Autowired
    private IMateriaService iMateriaService;

    @ApiOperation(value = "Listar todas as materias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibidas com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping
    public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
        Response<List<MateriaDto>> response = new Response<>();
        response.setData(this.iMateriaService.listarMaterias());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Consultar materia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Materia encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Materia não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<MateriaDto>> consultarMateria(@PathVariable Long id) {
        Response<MateriaDto> response = new Response<>();
        MateriaDto materiaDto = this.iMateriaService.consultarMateria(id);
        response.setData(this.iMateriaService.consultarMateria(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Listar todas as materias por hora minima")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibidas com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/horario-minimo/{horaMinima}")
    public ResponseEntity<Response<List<MateriaDto>>> listarMateriasPorHoraMinima(@PathVariable int horaMinima) {
        Response<List<MateriaDto>> response = new Response<>();
        List<MateriaDto> materiaDto = this.iMateriaService.listarMateriasPorHorarioMinimo(horaMinima);
        response.setData(materiaDto);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMateriasPorHoraMinima(horaMinima))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Listar todas as materias por frequencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibidas com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
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

    @ApiOperation(value = "Cadastrar uma nova materia")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 500, message = "Erro interno no serviço")
    })
    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.cadastrarMateria(materiaDto));
        response.setStatusCode(HttpStatus.CREATED.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).cadastrarMateria(materiaDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @ApiOperation(value = "Atualizar uma materia")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Materia atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 404, message = "Materia não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.atualizarMateria(materiaDto));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materiaDto))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(materiaDto.getId()))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Excluir uma materia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Materia excluída com sucesso"),
            @ApiResponse(code = 404, message = "Materia não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {
        Response<Boolean> response = new Response<>();
        response.setData(this.iMateriaService.excluirMateria(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
