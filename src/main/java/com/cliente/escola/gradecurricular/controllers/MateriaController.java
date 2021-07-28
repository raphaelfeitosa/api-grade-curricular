package com.cliente.escola.gradecurricular.controllers;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import com.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import com.cliente.escola.gradecurricular.services.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private IMateriaService iMateriaService;

    @GetMapping
    public ResponseEntity<List<MateriaDto>> listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iMateriaService.listarMaterias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDto> consultarMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iMateriaService.consultarMateria(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iMateriaService.cadastrarMateria(materiaDto));
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@Valid @RequestBody MateriaDto materiaDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iMateriaService.atualizarMateria(materiaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iMateriaService.excluirMateria(id));
    }

}
