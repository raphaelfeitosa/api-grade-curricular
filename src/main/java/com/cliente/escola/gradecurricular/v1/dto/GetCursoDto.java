package com.cliente.escola.gradecurricular.v1.dto;

import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetCursoDto extends RepresentationModel<GetCursoDto> {

    private Long id;

    private String nome;

    private String codigo;

    List<MateriaEntity> materias;

    public GetCursoDto(CursoEntity cursoEntity) {
        id = cursoEntity.getId();
        nome = cursoEntity.getNome();
        codigo = cursoEntity.getCodigo();
        materias = cursoEntity.getMaterias();
    }
}
